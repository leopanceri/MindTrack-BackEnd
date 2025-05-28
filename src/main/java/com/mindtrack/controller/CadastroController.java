package com.mindtrack.controller;


import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/cadastro/novo")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> novoCadastro(@RequestBody CadastroDTO cadastroDTO){
        try{
            return usuarioService.cadastrarUsuario(cadastroDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/cadastros")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<List<CadastroDTO>> listarCadastros(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.retornaTodosCadastrados());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/cadastros/{id}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        try {
            CadastroDTO cadastroDTO = usuarioService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(cadastroDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/cadastros/editar")
    @PreAuthorize("hasAuthority('ADM')")//ainda não está implementado
    public ResponseEntity<?> atualizaCadastro(@RequestBody CadastroDTO c){
        try{
            usuarioService.atualizaCadastro(c);
            return ResponseEntity.status(HttpStatus.OK).body("Cadastro atualizado com sucesso!");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na atualização cadastral");
        }
    }
    @DeleteMapping("/cadastros/{id}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id){
        try{
            usuarioService.inativaCadastro(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
