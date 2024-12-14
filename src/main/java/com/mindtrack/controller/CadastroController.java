package com.mindtrack.controller;


import com.mindtrack.entity.CadastroDTO;
import com.mindtrack.services.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @PostMapping("/cadastro/novo")
    public ResponseEntity<?> novoCadastro(@RequestBody CadastroDTO cadastroDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(cadastroService.cadastrarUsuario(cadastroDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass());
        }
    }
}
