package com.mindtrack.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mindtrack.entity.*;
import com.mindtrack.entity.dto.relatorio.*;
import com.mindtrack.repository.OpcaoRespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtrack.repository.SurveyRepository;

import jakarta.persistence.EntityNotFoundException;

import com.mindtrack.repository.SurveyReplayRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelatorioService {

    @Autowired
    private SurveyRepository surveyRepository;
    
    @Autowired
    private SurveyReplayRepository surveyReplayRepository;

    @Autowired
    private OpcaoRespostaRepository opcaoRespostaRepository;


    @Transactional(readOnly = true)
    public RelatorioQuestionarioDTO gerarRelatorio(Long questionarioId){
        Questionario questionario = surveyRepository.findById(questionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Questionário não encontrado com o ID: " + questionarioId));

        List<Pergunta> perguntasQuestionario = questionario.getPerguntas(); //surveyReplayRepository.perguntasPorQuestionario(questionarioId);

        Long totalParticipantes = surveyReplayRepository.totalRespostasPorQuestionario(questionarioId);

        List<RespostaQuestionario> respostasLiker = surveyReplayRepository.findRespostasComValorByQuestionarioId(questionarioId);

        Map<Funcionario, Integer> somaPorFuncionario = respostasLiker.stream()
                .collect(Collectors.groupingBy(
                        RespostaQuestionario::getFuncionario,
                        Collectors.summingInt(rq -> rq.getResposta().getValor())
                ));
        Double mediaGeralQuestionario = somaPorFuncionario.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        Map<String, Double> mediaPorSetorMap = somaPorFuncionario.entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().getSetor(),
                        Collectors.averagingDouble(Map.Entry::getValue)
                ));
        List<MediaPorSetorDTO> mediaPorSetor = mediaPorSetorMap.entrySet().stream()
                .map(entry -> new MediaPorSetorDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());


        //Double mediaGeralQuestionario = surveyReplayRepository.findAverageValorByQuestionario(questionarioId);
       // List<MediaPorSetorDTO> mediaPorSetor = surveyReplayRepository.findAverageValorBySetor(questionarioId);

        List<PerguntaRelatorioDTO> perguntasRelatorio = new ArrayList<>();
        for(Pergunta pergunta : perguntasQuestionario){
            if(!pergunta.getTipo().equals("ABERTA")){
                perguntasRelatorio.add(processarPerguntaMultiplaEscolha(questionarioId, pergunta));
            }else{
                perguntasRelatorio.add(processarPerguntaAberta(questionarioId, pergunta));
            }
        }

        return new RelatorioQuestionarioDTO(
                questionario.getId(),
                questionario.getTitulo(),
                questionario.getDescricao(),
                questionario.getDataPublicacao(),
                questionario.getDataValidade(),
                totalParticipantes,
                mediaGeralQuestionario,
                mediaPorSetor,
                perguntasRelatorio
        );

    }

    private PerguntaRelatorioDTO processarPerguntaMultiplaEscolha(Long questionarioId, Pergunta pergunta) {

        List<OpcaoResposta> todasAsOpcoes = opcaoRespostaRepository.findByPerguntaId(pergunta.getId());

        List<RespostaAgrupadaDTO> respostasAgrupadas = surveyReplayRepository.countRespostasByOpcao(questionarioId, pergunta.getId());

        Map<Long, Long> mapaDeContagens = respostasAgrupadas.stream()
                .collect(Collectors.toMap(RespostaAgrupadaDTO::idOpcaoResposta, RespostaAgrupadaDTO::quantidade));

        long totalRespostasPergunta = respostasAgrupadas.stream().mapToLong(RespostaAgrupadaDTO::quantidade).sum();
        DecimalFormat df = new DecimalFormat("0.00'%'");

        List<RespostaAgrupadaDTO> respostasAgrupadasFinal = todasAsOpcoes.stream()
                .map(opcao -> {
                    long contagem = mapaDeContagens.getOrDefault(opcao.getId(), 0L);
                    double percentual = (totalRespostasPergunta > 0) ? ((double) contagem / totalRespostasPergunta) * 100 : 0;
                    return new RespostaAgrupadaDTO(opcao.getId(), opcao.getTextoOpcao(), contagem, df.format(percentual));
                })
                .collect(Collectors.toList());

        return new PerguntaRelatorioDTO(
                pergunta.getId(),
                pergunta.getTexto(),
                pergunta.getTipo(),
                respostasAgrupadasFinal,
                null // Não há respostas individuais
        );
    }

    private PerguntaRelatorioDTO processarPerguntaAberta(Long questionarioId, Pergunta pergunta) {
        List<RespostaAbertaDTO> respostasIndividuais = surveyReplayRepository.findRespostasAbertas(questionarioId, pergunta.getId());

        return new PerguntaRelatorioDTO(
                pergunta.getId(),
                pergunta.getTexto(),
                pergunta.getTipo(),
                null, // Não há respostas agrupadas
                respostasIndividuais
        );
    }
    /*

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
                : String.valueOf(resposta.getResposta()));  // Trata tanto resposta aberta quanto numérica
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

     */
}
