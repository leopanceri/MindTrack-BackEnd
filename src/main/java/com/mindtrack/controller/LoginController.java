package com.mindtrack.controller;

import com.mindtrack.entity.dto.LoginRequestDTO;
import com.mindtrack.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody LoginRequestDTO login){
        try{
            return usuarioService.autenticarUsuario(login);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return usuarioService.logoutUser();
    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<?> esqueciMinhaSenha(@RequestBody String email){
        try{
            usuarioService.requisitaNovaSenha(email);
            return ResponseEntity.status(HttpStatus.OK).body("Link enviado por email!");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/cadastroSenha")
    public ResponseEntity<?> cadastroSenha(@RequestParam String token, @RequestBody String novaSenha){
        if(usuarioService.cadastraNovaSenha(token, novaSenha)){
            return ResponseEntity.status(HttpStatus.OK).body("Nova Senha Cadastrada!");
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token inválido!");
        }
}
