package com.mindtrack.controller;

import com.mindtrack.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<?> getFuncionario(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.findById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<?> obterTodosFuncionarios(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass());
        }
    }

    /*

    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable Long id){
        try{
            funcionarioService.deleteFuncionario(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass());
        }
    }

     */
}
