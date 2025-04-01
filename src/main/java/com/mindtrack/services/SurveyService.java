package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Question;
import com.mindtrack.entity.Survey;
import com.mindtrack.entity.dto.SurveyDTO;
import com.mindtrack.entity.dto.SurveyResponseDTO;
import com.mindtrack.repository.SurveyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ModelMapper surveyMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    AdministradorService administradorService;


    public void crirNovoQuestionario(SurveyDTO surveyDTO) {
        List<Question> questions = questionService.listarTodasPerguntasPorId(surveyDTO.getQuestionsId());
        Administrador administrador = administradorService.findById((long) surveyDTO.getAdmId());
        Survey survey = new Survey(
                null,
                surveyDTO.getPublicationDate(),
                surveyDTO.getDueDate(),
                surveyDTO.getTitle(),
                surveyDTO.getDescription(),
                questions,
                administrador
        );
        surveyRepository.save(survey);
    }

    public List<SurveyResponseDTO> listaQuestionarios() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream().map(e-> surveyMapper.map(e, SurveyResponseDTO.class)).collect(Collectors.toList());
    }

    public SurveyResponseDTO questionarioById(Long id) {
        return surveyRepository.findById(id).map(e-> surveyMapper.map(e, SurveyResponseDTO.class)).
                orElseThrow(()->new RuntimeException("Questionário não encontrado!"));
    }
}
