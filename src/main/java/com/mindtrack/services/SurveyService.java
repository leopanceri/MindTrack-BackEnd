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
import java.util.NoSuchElementException;
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
                false,
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

    public Survey findSurveyById(Long id) {
        return surveyRepository.findById(id).orElseThrow(()->new RuntimeException("Questionário não encontrado 2!"));
    }

    public List<SurveyResponseDTO> listaQuestionariosNaoRespondidos(Long funcionarioId) {
        List<Survey> surveys = surveyRepository.findQuestionariosNaoRespondidosPorUsuario(funcionarioId);
        if (surveys.isEmpty()) {
            throw new NoSuchElementException("Nenhum questionário disponível para o funcionário informado.");
        }
        return surveys.stream().map(e-> surveyMapper.map(e, SurveyResponseDTO.class)).collect(Collectors.toList());
    }

    public void editarQuestionario(Long id, SurveyDTO surveyDTO) {
        Survey survey = surveyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Questionário não encontrado para edição!"));

        if (survey.isPublic()) {
            throw new RuntimeException("Questionários publicados não podem ser editados.");
        }

        survey.setPublicationDate(surveyDTO.getPublicationDate());
        survey.setDueDate(surveyDTO.getDueDate());
        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());

        List<Question> questions = questionService.listarTodasPerguntasPorId(surveyDTO.getQuestionsId());
        survey.setQuestions(questions);

        Administrador administrador = administradorService.findById((long) surveyDTO.getAdmId());
        survey.setPublisher(administrador);

        surveyRepository.save(survey);
    }

    public void removerQuestionario(Long id) {
        Survey survey = surveyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Questionário não encontrado para remoção!"));

        // ✅ Verifica se está publicado
        if (survey.isPublic()) {
            throw new RuntimeException("Questionários publicados não podem ser excluídos.");
        }

        surveyRepository.deleteById(id);
    }
}
