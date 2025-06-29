package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.dto.QuestionarioDTO;
import com.mindtrack.entity.dto.QuestionarioResponseDTO;
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


    public void criarNovoQuestionario(QuestionarioDTO questionarioDTO) {
        List<Pergunta> perguntas = questionService.listarTodasPerguntasPorId(questionarioDTO.getPerguntasId());
        Administrador administrador = administradorService.buscaPorId((long) questionarioDTO.getAdmId());
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

    public List<QuestionarioResponseDTO> listaQuestionarios() {
        List<Questionario> questionarios = surveyRepository.findAll();
        return questionarios.stream().map(e-> surveyMapper.map(e, QuestionarioResponseDTO.class)).collect(Collectors.toList());
    }

    public QuestionarioResponseDTO buscarPorId(Long id) {
        return surveyRepository.findById(id).map(e-> surveyMapper.map(e, QuestionarioResponseDTO.class)).
                orElseThrow(()->new RuntimeException("Questionário não encontrado!"));
    }

    public Questionario findSurveyById(Long id) {
        return surveyRepository.findById(id).orElseThrow(()->new RuntimeException("Questionário não encontrado 2!"));
    }

    public List<QuestionarioResponseDTO> listaNaoRespondidosPorFuncionario(Long funcionarioId) {
        List<Questionario> questionarios = surveyRepository.findQuestionariosNaoRespondidosPorUsuario(funcionarioId);
        if (questionarios.isEmpty()) {
            throw new NoSuchElementException("Nenhum questionário disponível para o funcionário informado.");
        }
        return questionarios.stream().map(e-> surveyMapper.map(e, QuestionarioResponseDTO.class)).collect(Collectors.toList());
    }


    public void editarQuestionario(Long id, QuestionarioDTO surveyDTO) {
        Questionario survey = surveyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Questionário não encontrado para edição!"));

        if (survey.isPublico()) {
            throw new RuntimeException("Questionários publicados não podem ser editados.");
        }

        survey.setDataPublicacao(surveyDTO.getDataPublicacao());
        survey.setDataValidade(surveyDTO.getDataValidade());
        survey.setTitulo(surveyDTO.getTitulo());
        survey.setDescricao(surveyDTO.getDescricao());

        List<Pergunta> questions = questionService.listarTodasPerguntasPorId(surveyDTO.getPerguntasId());
        survey.setPerguntas(questions);

        Administrador administrador = administradorService.buscaPorId((long) surveyDTO.getAdmId());
        survey.setResponsavel(administrador);

        surveyRepository.save(survey);
    }

    public void removerQuestionario(Long id) {
        Questionario survey = surveyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Questionário não encontrado para remoção!"));

        // ✅ Verifica se está publicado
        if (survey.isPublico()) {
            throw new RuntimeException("Questionários publicados não podem ser excluídos.");
        }

        surveyRepository.deleteById(id);
    }
}
