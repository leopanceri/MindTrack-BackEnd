package com.mindtrack.services;

import com.mindtrack.entity.Usuario;
import com.mindtrack.entity.dto.LoginRequestDTO;
import com.mindtrack.entity.dto.LoginResponseDTO;
import com.mindtrack.enums.Status;
import com.mindtrack.repository.UsuarioRepository;
import com.mindtrack.security.JwtUtils;
import com.mindtrack.security.UserDetailsImpl;
import com.mindtrack.services.helpers.EmailService;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordTokenService passworTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

    public ResponseEntity<?> logout() {
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
