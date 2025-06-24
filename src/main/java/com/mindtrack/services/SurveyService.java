package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.dto.QuestionarioDTO;
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


    public void crirNovoQuestionario(QuestionarioDTO questionarioDTO) {
        List<Pergunta> perguntas = questionService.listarTodasPerguntasPorId(questionarioDTO.getPerguntasId());
        Administrador administrador = administradorService.findById((long) questionarioDTO.getAdmId());
        Questionario questionario = new Questionario(
                null,
                questionarioDTO.getDataPublicacao(),
                questionarioDTO.getDataValidade(),
                questionarioDTO.getTitulo(),
                questionarioDTO.getDescricao(),
                false,
                perguntas,
                administrador
        );
        surveyRepository.save(questionario);
    }

    public List<SurveyResponseDTO> listaQuestionarios() {
        List<Questionario> questionarios = surveyRepository.findAll();
        return questionarios.stream().map(e-> surveyMapper.map(e, SurveyResponseDTO.class)).collect(Collectors.toList());
    }

    public SurveyResponseDTO questionarioById(Long id) {
        return surveyRepository.findById(id).map(e-> surveyMapper.map(e, SurveyResponseDTO.class)).
                orElseThrow(()->new RuntimeException("Questionário não encontrado!"));
    }

    public Questionario findSurveyById(Long id) {
        return surveyRepository.findById(id).orElseThrow(()->new RuntimeException("Questionário não encontrado 2!"));
    }

    public List<SurveyResponseDTO> listaQuestionariosNaoRespondidos(Long funcionarioId) {
        List<Questionario> questionarios = surveyRepository.findQuestionariosNaoRespondidosPorUsuario(funcionarioId);
        if (questionarios.isEmpty()) {
            throw new NoSuchElementException("Nenhum questionário disponível para o funcionário informado.");
        }
        return questionarios.stream().map(e-> surveyMapper.map(e, SurveyResponseDTO.class)).collect(Collectors.toList());
    }

    public void removerQuestionario(Long id) {
        surveyRepository.deleteById(id);
    }
}
