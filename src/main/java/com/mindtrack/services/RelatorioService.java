package com.mindtrack.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtrack.repository.SurveyRepository;

import jakarta.persistence.EntityNotFoundException;

import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.RespostaQuestionario;
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
        Questionario questionario = surveyRepository.findById(surveyId)
            .orElseThrow(() -> new EntityNotFoundException("Questionário não encontrado"));

        long totalRespostas = SurveyReplayRepository.countByQuestionario(questionario);

        List<RespostaQuestionario> respostas = SurveyReplayRepository.findByQuestionarioId(surveyId);

        List<Map<String, Object>> respostasDTO = respostas.stream().map(resposta -> {
            Map<String, Object> respostaMap = new HashMap<>();
            respostaMap.put("nomeFuncionario", resposta.getFuncionario().getNome());
            respostaMap.put("pergunta", resposta.getPergunta().getTexto());  // <-- Aqui é 'texto'!
            respostaMap.put("resposta", 
                resposta.getResposta_aberta() != null && !resposta.getResposta_aberta().isEmpty() 
                ? resposta.getResposta_aberta()
                        :resposta.getResposta().getTextoOpcao());
                //: String.valueOf(resposta.getResposta()));  // Trata tanto resposta aberta quanto numérica
            return respostaMap;
        }).toList();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("titulo", questionario.getTitulo());
        relatorio.put("dataPublicacao", questionario.getDataPublicacao());
        relatorio.put("dataValidade", questionario.getDataValidade());
        relatorio.put("quantidadeRespostas", totalRespostas);
        relatorio.put("respostas", respostasDTO); // <-- Inclui respostas aqui!

        return relatorio;
    }
}
