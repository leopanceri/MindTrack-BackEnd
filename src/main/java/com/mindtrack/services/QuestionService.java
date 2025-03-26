package com.mindtrack.services;

import com.mindtrack.entity.Question;
import com.mindtrack.entity.QuestionDTO;
import com.mindtrack.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;


    public void cadastrarNovaPergunta(String p) {
        Question question = new Question();
        question.setText(p);
        questionRepository.save(question);
    }

    public List<QuestionDTO> listarPerguntas() {
        List<Question> lista = questionRepository.findAll();
        return lista.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }
}
