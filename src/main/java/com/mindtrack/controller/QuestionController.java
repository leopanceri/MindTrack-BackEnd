package com.mindtrack.controller;

import com.mindtrack.entity.TemaPergunta;
import com.mindtrack.entity.dto.PerguntaDTO;
import com.mindtrack.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/novapergunta")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> novaPergunta(@RequestBody PerguntaDTO perguntaDTO) {
        try {
            //questionService.cadastrarNovaPergunta(questionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.cadastrarNovaPergunta(perguntaDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/listaperguntas")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> listarPerguntas(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.listarPerguntas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar Perguntas!");
        }
    }

    @GetMapping("/listartemapergunta")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> listarTemasPergunta() {
        try {
            return ResponseEntity.status(HttpStatus.OK). body(questionService.listartemas());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar Temas Perguntas!");
        }
    }

    @PostMapping("/novotema")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> adNovoTeMa(@RequestBody TemaPergunta novoTemaPergunta) {
        try {
            questionService.adicionarTema(novoTemaPergunta);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar Temas Perguntas!");
        }
    }
}
