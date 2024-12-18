package com.mindtrack.controller;


import com.mindtrack.entity.CadastroDTO;
import com.mindtrack.services.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/cadastros")
    public ResponseEntity<List<CadastroDTO>> listarCadastros(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cadastroService.retornaTodosCadastrados());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/cadastros/editar") //ainda não está implementado
    public void atualizaCadastro(@RequestBody CadastroDTO c){
        cadastroService.atualizaCadastro(c);
    }

    @DeleteMapping("/cadastros/{id}")
    public void removerUsuario(@PathVariable Long id){
        cadastroService.removeCadastro(id);
    }
}
