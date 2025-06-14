package com.mindtrack.services;

import com.mindtrack.entity.OpcaoResposta;
import com.mindtrack.entity.Question;
import com.mindtrack.entity.dto.QuestionDTO;
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

    public QuestionDTO cadastrarNovaPergunta(QuestionDTO questionDTO) {
        Question question = new Question(categoryConverter.convertToEntityAttribute(questionDTO.getCategory()), questionDTO.getText(),
                questionDTO.getTipo());

        questionDTO.getOpcoes().forEach(optDTO ->{
            OpcaoResposta opc = new OpcaoResposta(optDTO, question);
            question.getOpcoes().add(opc);
        });
        return questionMapper.map (questionRepository.save(question),QuestionDTO.class) ;
    }

    public List<QuestionDTO> listarPerguntas() {
        List<Question> lista = questionRepository.findAllWithOpts();
        return lista.stream().map(e-> questionMapper.map(e, QuestionDTO.class)).collect(Collectors.toList());
    }

    public List<Question> listarTodasPerguntasPorId(List<Long> ids) {
        List<Question> lista = questionRepository.findAllById(ids);
        return lista;
    }
}
