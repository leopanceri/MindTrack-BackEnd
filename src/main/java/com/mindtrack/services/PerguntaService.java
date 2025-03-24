package com.mindtrack.services;

import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.PerguntaDTO;
import com.mindtrack.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final ModelMapper mapperPergunta;

    public PerguntaService(PerguntaRepository perguntaRepository, ModelMapper mapperPergunta) {
        this.perguntaRepository = perguntaRepository;
        this.mapperPergunta = mapperPergunta;
    }

    public void cadastrarNovaPergunta(String p) {
        Pergunta pergunta = new Pergunta();
        pergunta.setTexto(p);
        perguntaRepository.save(pergunta);
    }

    public List<PerguntaDTO> listarPerguntas() {
        List<Pergunta> lista = perguntaRepository.findAll();
        return lista.stream().map(e -> mapperPergunta.map(e, PerguntaDTO.class)).collect(Collectors.toList());
    }
}
