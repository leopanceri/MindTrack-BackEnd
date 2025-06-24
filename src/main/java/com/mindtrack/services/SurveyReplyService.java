package com.mindtrack.services;

import com.mindtrack.entity.*;
import com.mindtrack.entity.dto.ReplyDTO;
import com.mindtrack.repository.OpcaoRespostaRepository;
import com.mindtrack.repository.QuestionRepository;
import com.mindtrack.repository.SurveyReplayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyReplyService {

    @Autowired
    SurveyReplayRepository surveyReplayRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OpcaoRespostaRepository opcaoRepository;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    SurveyService surveyService;

    @Transactional
    public void salvarResposta(Long surveyId, Long funcionarioId, List<ReplyDTO> replies){
        Funcionario funcionario = funcionarioService.findById(funcionarioId);
        Questionario questionario = surveyService.findSurveyById(surveyId);

        List<RespostaQuestionario> replyList = new ArrayList<>();

        for (ReplyDTO dto : replies) {
            RespostaQuestionario reply = new RespostaQuestionario();
            reply.setFuncionario(funcionario);
            reply.setQuestionario(questionario);
            Pergunta pergunta = questionRepository.findById(dto.getPerguntaId())
                    .orElseThrow(() -> new RuntimeException("Pergunta não encontrada com id: " + dto.getPerguntaId()));
            reply.setPergunta(pergunta);
            if (dto.getOpcaoId() != null){
                OpcaoResposta opcao = opcaoRepository.findById(dto.getOpcaoId())
                        .orElseThrow(() -> new RuntimeException("Opção de resposta não encontrada com id: " + dto.getPerguntaId()));
                reply.setResposta(opcao);
            }
            if (dto.getTexto() != null && !dto.getTexto().isEmpty()){
                reply.setResposta_aberta(dto.getTexto());
            }

            replyList.add(reply);
        }
        surveyReplayRepository.saveAll(replyList);
    }
}
