package com.mindtrack.controller;

import com.mindtrack.entity.CadastroDTO;
import com.mindtrack.services.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    @PostMapping("/novapergunta")
    public ResponseEntity<?> novaPergunta(@RequestBody String p){
        try {
            perguntaService.cadastrarNovaPergunta(p);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pergunta criada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar Pergunta!");
        }
    }

    @GetMapping("/listaperguntas")
    public ResponseEntity<?> listarPerguntas(){
        try {
            return ResponseEntity.status(HttpStatus.OK). body(perguntaService.listarPerguntas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar Perguntas!");
        }
    }
}
