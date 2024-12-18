package com.mindtrack.controller;


import com.mindtrack.entity.CadastroDTO;
import com.mindtrack.services.CadastroService;
import com.mindtrack.services.UsuarioService;

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

    @GetMapping("/cadastros/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        try {
            CadastroDTO cadastroDTO = cadastroService.findById(id);  
            return ResponseEntity.status(HttpStatus.OK).body(cadastroDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/cadastros/editar") //ainda não está implementado
    public ResponseEntity<?> atualizaCadastro(@RequestBody CadastroDTO c){
        try{
            cadastroService.atualizaCadastro(c);
            return ResponseEntity.status(HttpStatus.OK).body("Cadastro Atualizado");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na atualização cadastral");
        }
    }

    @DeleteMapping("/cadastros/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id){
        try{
            cadastroService.removeCadastro(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
