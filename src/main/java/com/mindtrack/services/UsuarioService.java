package com.mindtrack.services;

import com.mindtrack.entity.*;

import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.entity.dto.LoginRequestDTO;
import com.mindtrack.entity.dto.LoginResponseDTO;
import com.mindtrack.enums.Status;
import com.mindtrack.repository.UsuarioRepository;
import com.mindtrack.security.JwtUtils;
import com.mindtrack.security.UserDetailsImpl;
import com.mindtrack.services.helpers.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@Service
public class UsuarioService {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordTokenService passworTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> cadastrarUsuario(CadastroDTO newCadastro) {
        //if(usuarioRepository.existsByEmail(newCadastro.getEmail()))
          //  return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado!");
        if (Objects.equals(newCadastro.getPerfil(), "Funcionário")) {
            Funcionario func = new Funcionario(newCadastro);
            func = funcionarioService.createFuncionario(func);
            String resetLink = passworTokenService.criaLinkResetPassword(func, 24*60);
            emailService.enviaEmailCadastro(resetLink, "password_email", func);

            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(func, CadastroDTO.class));

        }else {
            Administrador adm = new Administrador(newCadastro);
            adm = administradorService.createAdministrador(adm);
            String resetLink = passworTokenService.criaLinkResetPassword(adm, 24*60);
            emailService.enviaEmailCadastro(resetLink, "password_email", adm);

            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(adm, CadastroDTO.class));
        }
    }

    public ResponseEntity<?> autenticarUsuario(LoginRequestDTO loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ResponseCookie jwtCookie = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new LoginResponseDTO(
                    userDetails.getId(),
                    userDetails.getName(),
                    userDetails.getUsername(),
                    roles));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERRO AQUI: " + e.getMessage());
        }
    }

   public List<CadastroDTO> retornaTodosCadastrados(){
        List<Usuario> cadastroList = usuarioRepository.findAll();
        return cadastroList.stream().map(e -> mapper.map(e, CadastroDTO.class)).collect(Collectors.toList());
   }


    public CadastroDTO findById(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + idUsuario);
        }
        return mapper.map(usuario.get(), CadastroDTO.class);
    }    
    

    public void atualizaCadastro(CadastroDTO c) {
        if(Objects.equals(c.getPerfil(), "Funcionário")) {
            funcionarioService.updateFuncionario(c);
        } else if (Objects.equals(c.getPerfil(), "Administrador")) {
            administradorService.updateAdministrador(c);
        }else{
            throw new RuntimeException("Perfil informado é inválido!");
        }
    }

    public void inativaCadastro(Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado com o ID: " + id));
        u.setStatus(Status.INATIVO);
        usuarioRepository.save(u);
    }

    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Usuário desconectado com sucesso");
    }

    public void requisitaNovaSenha(String email) {
        Usuario user = usuarioRepository.findByEmail(email).orElseThrow();
        String resetLink = passworTokenService.criaLinkResetPassword(user, 3*60);
        emailService.enviaEmailRecuperaSenha(resetLink, "password_email", user);
    }

    public boolean cadastraNovaSenha(String token, String novaSenha) {
        Usuario user = passworTokenService.validaToken(token);
        if(user != null){
            user.setSenha(passwordEncoder.encode(novaSenha));
            user.setStatus(Status.ATIVO);
            usuarioRepository.save(user);
            passworTokenService.removeTokemExistente(user);
            return true;
        }else{
            return false;
        }
    }
}
