package com.mindtrack.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtrack.repository.SurveyRepository;

import jakarta.persistence.EntityNotFoundException;

import com.mindtrack.entity.Survey;
import com.mindtrack.entity.SurveyReply;
import com.mindtrack.repository.SurveyReplayRepository;

@Service
public class RelatorioService {

    @Autowired
    private SurveyRepository surveyRepository;
    
    @Autowired
    private SurveyReplayRepository SurveyReplayRepository;

    public RelatorioService(SurveyRepository surveyRepository, SurveyReplayRepository SurveyReplayRepository) {
        this.surveyRepository = surveyRepository;
        this.SurveyReplayRepository = SurveyReplayRepository;
    }

    public Map<String, Object> gerarRelatorioQuestionario(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
            .orElseThrow(() -> new EntityNotFoundException("Questionário não encontrado"));

        long totalRespostas = SurveyReplayRepository.countBySurvey(survey);

        List<SurveyReply> respostas = SurveyReplayRepository.findBySurveyId(surveyId);

        List<Map<String, Object>> respostasDTO = respostas.stream().map(resposta -> {
            Map<String, Object> respostaMap = new HashMap<>();
            respostaMap.put("nomeFuncionario", resposta.getFuncionario().getNome());
            respostaMap.put("pergunta", resposta.getQuestion().getText());  // <-- Aqui é 'texto'!
            respostaMap.put("resposta", 
                resposta.getResposta_aberta() != null && !resposta.getResposta_aberta().isEmpty() 
                ? resposta.getResposta_aberta() 
                : String.valueOf(resposta.getResposta()));  // Trata tanto resposta aberta quanto numérica
            return respostaMap;
        }).toList();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("titulo", survey.getTitle());
        relatorio.put("dataPublicacao", survey.getPublicationDate());
        relatorio.put("dataValidade", survey.getDueDate());
        relatorio.put("quantidadeRespostas", totalRespostas);
        relatorio.put("respostas", respostasDTO); // <-- Inclui respostas aqui!

        return relatorio;
    }
}
