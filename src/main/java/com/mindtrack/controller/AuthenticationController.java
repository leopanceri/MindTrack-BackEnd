package com.mindtrack.controller;

import com.mindtrack.entity.dto.LoginRequestDTO;
import com.mindtrack.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody LoginRequestDTO login){
        try{
            return authService.autenticarUsuario(login);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return authService.logout();
    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<?> esqueciMinhaSenha(@RequestBody String email){
        try{
            authService.requisitaNovaSenha(email);
            return ResponseEntity.status(HttpStatus.OK).body("Um link para recuperação foi enviado por email!");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/cadastroSenha")
    public ResponseEntity<?> cadastroSenha(@RequestParam String token, @RequestBody String novaSenha){
        if(authService.cadastraNovaSenha(token, novaSenha)){
            return ResponseEntity.status(HttpStatus.OK).body("Nova Senha Cadastrada!");
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token inválido!");
        }
}
