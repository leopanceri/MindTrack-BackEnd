package com.mindtrack.controller;

import com.mindtrack.entity.QuestionDTO;
import com.mindtrack.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/novapergunta")
    public ResponseEntity<?> novaPergunta(@RequestBody QuestionDTO questionDTO) {
        try {
            questionService.cadastrarNovaPergunta(questionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pergunta criada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar Pergunta!");
        }
    }

    @GetMapping("/listaperguntas")
    public ResponseEntity<?> listarPerguntas(){
        try {
            return ResponseEntity.status(HttpStatus.OK). body(questionService.listarPerguntas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar Perguntas!");
        }
    }
}
