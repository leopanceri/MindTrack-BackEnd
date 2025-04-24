package com.mindtrack.services;

import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Question;
import com.mindtrack.entity.Survey;
import com.mindtrack.entity.SurveyReply;
import com.mindtrack.entity.dto.ReplyDTO;
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
    FuncionarioService funcionarioService;

    @Autowired
    SurveyService surveyService;

    @Transactional
    public void salvarResposta(Long surveyId, Long funcionarioId, List<ReplyDTO> replies){
        Funcionario funcionario = funcionarioService.findById(funcionarioId);
        Survey survey = surveyService.findSurveyById(surveyId);

        List<SurveyReply> replyList = new ArrayList<>();

        for (ReplyDTO dto : replies) {
            SurveyReply reply = new SurveyReply();
            reply.setFuncionario(funcionario);
            reply.setSurvey(survey);
            //System.out.println("ID da pergunta: " + dto.getQuestionId());
            reply.setQuestion(new Question(dto.getQuestionId()));
            reply.setReply(dto.getReply());
            replyList.add(reply);
        }
        surveyReplayRepository.saveAll(replyList);
    }
}
