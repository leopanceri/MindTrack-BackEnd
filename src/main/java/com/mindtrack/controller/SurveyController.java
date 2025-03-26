package com.mindtrack.controller;

import com.mindtrack.entity.Question;
import com.mindtrack.entity.Survey;
import com.mindtrack.entity.SurveyDTO;
import com.mindtrack.repository.QuestionRepository;
import com.mindtrack.repository.SurveyRepository;
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
    private  SurveyRepository surveyRepository;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private QuestionRepository questionRepository;




    @PostMapping("/novoquestionario")
    public ResponseEntity<?> criarNovoQuestionario(@RequestBody SurveyDTO surveyDTO) {
        try {
            List<Question> questions = questionRepository.findAllById(surveyDTO.getQuestionsId());
            Survey survey = new Survey(
                    null,
                    surveyDTO.getPublicationDate(),
                    surveyDTO.getDueDate(),
                    surveyDTO.getTitle(),
                    surveyDTO.getDescription(),
                    questions
            );
            surveyRepository.save(survey);
            return ResponseEntity.status(HttpStatus.CREATED).body("Questionário criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao criar questionário");
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

}
