package com.mindtrack.services;

import com.mindtrack.entity.Question;
import com.mindtrack.entity.QuestionDTO;
import com.mindtrack.enums.converters.CategoryConverter;
import com.mindtrack.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper questionMapper;

    private final CategoryConverter categoryConverter = new CategoryConverter();

    public void cadastrarNovaPergunta(QuestionDTO questionDTO) {
        Question question = new Question(null, categoryConverter.convertToEntityAttribute(questionDTO.getCategory()), questionDTO.getText());
        questionRepository.save(question);
    }

    public List<QuestionDTO> listarPerguntas() {
        List<Question> lista = questionRepository.findAll();
        return lista.stream().map(e-> questionMapper.map(e, QuestionDTO.class)).collect(Collectors.toList());
    }
}
