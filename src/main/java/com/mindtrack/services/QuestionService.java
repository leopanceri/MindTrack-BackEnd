package com.mindtrack.services;

import com.mindtrack.entity.OpcaoResposta;
import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.TemaPergunta;
import com.mindtrack.entity.dto.PerguntaDTO;
import com.mindtrack.enums.converters.CategoryConverter;
import com.mindtrack.repository.QuestionRepository;
import com.mindtrack.repository.TemaPerguntaRepository;
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
    TemaPerguntaRepository temaPerguntaRepository;

    @Autowired
    private ModelMapper questionMapper;

    private final CategoryConverter categoryConverter = new CategoryConverter();

    public PerguntaDTO cadastrarNovaPergunta(PerguntaDTO perguntaDTO) {
        TemaPergunta tema = temaPerguntaRepository.findById(perguntaDTO.getIdTema()).get();
        Pergunta pergunta = new Pergunta(tema, perguntaDTO.getTexto(), perguntaDTO.getTipo());

        perguntaDTO.getOpcoes().forEach(optDTO ->{
            OpcaoResposta opc = new OpcaoResposta(optDTO, pergunta);
            pergunta.getOpcoes().add(opc);
        });
        return questionMapper.map (questionRepository.save(pergunta), PerguntaDTO.class) ;
    }

    public List<PerguntaDTO> listarPerguntas() {
        List<Pergunta> lista = questionRepository.findAllWithOpts();
        return lista.stream().map(e-> questionMapper.map(e, PerguntaDTO.class)).collect(Collectors.toList());
    }

    public List<Pergunta> listarTodasPerguntasPorId(List<Long> ids) {
        List<Pergunta> lista = questionRepository.findAllById(ids);
        return lista;
    }

    public List<TemaPergunta> listartemas() {
        List<TemaPergunta> lista = temaPerguntaRepository.findAll();
        return lista;
    }

    public void adicionarTema(TemaPergunta novoTemaPergunta) {
        temaPerguntaRepository.save(novoTemaPergunta);
    }
}
