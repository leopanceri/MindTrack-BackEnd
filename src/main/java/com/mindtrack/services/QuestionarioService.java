package com.mindtrack.services;

import com.mindtrack.entity.Questionario;
import com.mindtrack.repository.QuestionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionarioService {

    private final QuestionarioRepository questionarioRepository;

    public QuestionarioService(QuestionarioRepository questionarioRepository) {
        this.questionarioRepository = questionarioRepository;
    }
    public List<Questionario> listaQuestionarios() {
        return questionarioRepository.findAll();
    }
}
