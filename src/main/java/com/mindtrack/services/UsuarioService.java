package com.mindtrack.services;

import com.mindtrack.entity.*;

import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.entity.dto.LoginRequestDTO;
import com.mindtrack.entity.dto.LoginResponseDTO;
import com.mindtrack.repository.UsuarioRepository;
import com.mindtrack.security.JwtUtils;
import com.mindtrack.security.UserDetailsImpl;
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


import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;


    public ResponseEntity<?> cadastrarUsuario(CadastroDTO newCadastro) {
        //if(usuarioRepository.existsByEmail(newCadastro.getEmail()))
          //  return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado!");
        if (Objects.equals(newCadastro.getPerfil(), "Funcionário")) {
            Funcionario func = new Funcionario(newCadastro);
            funcionarioService.createFuncionario(func);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(func, CadastroDTO.class));

        }else {
            Administrador adm = new Administrador(newCadastro);
            administradorService.createAdministrador(adm);
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
       //List<CadastroInterface> cadastroList = usuarioRepository.buscarUsuariosCadastrados();
       //return cadastroList.stream().map(CadastroDTO::new).collect(Collectors.toList());
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
        u.setStatus("Inativo");
        usuarioRepository.save(u);
    }

    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Usuário desconectado com sucesso");
    }
}
