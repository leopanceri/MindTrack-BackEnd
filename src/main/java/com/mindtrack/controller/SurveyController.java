package com.mindtrack.controller;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Question;
import com.mindtrack.entity.Survey;
import com.mindtrack.entity.dto.SurveyDTO;
import com.mindtrack.entity.dto.SurveyResponseDTO;
import com.mindtrack.repository.QuestionRepository;
import com.mindtrack.repository.SurveyRepository;
import com.mindtrack.services.AdministradorService;
import com.mindtrack.services.QuestionService;
import com.mindtrack.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;


    @PostMapping("/novoquestionario")
    public ResponseEntity<?> criarNovoQuestionario(@RequestBody SurveyDTO surveyDTO) {
        try {
            surveyService.crirNovoQuestionario(surveyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Questionário criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao criar questionário: " + e.getMessage());
        }
    }

    @GetMapping("/listaquestionarios")
    public ResponseEntity<?> listarQuestionarios() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(surveyService.listaQuestionarios());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao listar questionário");
        }
    }

    @GetMapping("/questionario/{id}")
    public ResponseEntity<?> findQuestionarioById(@PathVariable int id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(surveyService.questionarioById((long) id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
