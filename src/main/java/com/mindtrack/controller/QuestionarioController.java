package com.mindtrack.controller;

import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.QuestionarioDTO;
import com.mindtrack.repository.PerguntaRepository;
import com.mindtrack.repository.QuestionarioRepository;
import com.mindtrack.services.QuestionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class QuestionarioController {

    private final QuestionarioRepository questionarioRepository;
    private final QuestionarioService questionarioService;
    private final PerguntaRepository perguntaRepository;


    public QuestionarioController(QuestionarioRepository questionarioRepository, PerguntaRepository perguntaRepository,
                                  QuestionarioService questionarioService) {
        this.questionarioRepository = questionarioRepository;
        this.perguntaRepository = perguntaRepository;
        this.questionarioService = questionarioService;
    }

    @PostMapping("/novoquestionario")
    public ResponseEntity<?> criarNovoQuestionario(@RequestBody QuestionarioDTO questionarioDTO) {
        try {
            List<Pergunta> perguntas= perguntaRepository.findAllById(questionarioDTO.getPerguntasIds());
            Questionario questionario = new Questionario(
                    null,
                    questionarioDTO.getDataPubli(),
                    questionarioDTO.getDataVal(),
                    questionarioDTO.getTitulo(),
                    questionarioDTO.getDescricao(),
                    perguntas
            );
            questionarioRepository.save(questionario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Questionário criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao criar questionário");
        }
    }

    @GetMapping("/listaquestionarios")
    public ResponseEntity<?> listarQuestionarios() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(questionarioService.listaQuestionarios());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao listar questionário");
        }
    }

}
