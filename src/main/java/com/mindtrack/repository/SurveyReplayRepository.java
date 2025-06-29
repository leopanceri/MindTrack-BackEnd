package com.mindtrack.repository;

import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.RespostaQuestionario;

import java.util.List;

import com.mindtrack.entity.dto.relatorio.MediaPorSetorDTO;
import com.mindtrack.entity.dto.relatorio.RespostaAbertaDTO;
import com.mindtrack.entity.dto.relatorio.RespostaAgrupadaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyReplayRepository extends JpaRepository<RespostaQuestionario, Long> {
    List<RespostaQuestionario> findByQuestionarioId(Long id);
    long countByQuestionario(Questionario questionario);@Query("SELECT COUNT(DISTINCT rq.funcionario.id) FROM RespostaQuestionario rq WHERE rq.questionario.id = :questionarioId")


    Long totalRespostasPorQuestionario(@Param("questionarioId") Long questionarioId);

    // Busca as perguntas que foram respondidas em um questionário
    @Query("SELECT DISTINCT rq.pergunta FROM RespostaQuestionario rq WHERE rq.questionario.id = :questionarioId")
    List<Pergunta> perguntasPorQuestionario(@Param("questionarioId") Long questionarioId);

    /*
    // Agrupa e conta as respostas de múltipla escolha para uma pergunta
    @Query("SELECT new com.mindtrack.entity.dto.relatorio.RespostaAgrupadaDTO(op.id, op.textoOpcao, COUNT(rq.id), '0%') " +
            "FROM RespostaQuestionario rq " +
            "JOIN rq.resposta op " +
            "WHERE rq.questionario.id = :questionarioId AND rq.pergunta.id = :perguntaId " +
            "GROUP BY op.id, op.textoOpcao")
    List<RespostaAgrupadaDTO> countRespostasByOpcao(
            @Param("questionarioId") Long questionarioId,
            @Param("perguntaId") Long perguntaId
    );
    */
    @Query("SELECT new com.mindtrack.entity.dto.relatorio.RespostaAgrupadaDTO(op.id, op.textoOpcao, COUNT(rq.id), '0%') " +
            "FROM RespostaQuestionario rq " +
            "JOIN rq.resposta op " +
            "WHERE rq.questionario.id = :questionarioId AND rq.pergunta.id = :perguntaId " +
            "GROUP BY op.id, op.textoOpcao")
    List<RespostaAgrupadaDTO> countRespostasByOpcao(
            @Param("questionarioId") Long questionarioId,
            @Param("perguntaId") Long perguntaId
    );

    // Busca todas as respostas abertas para uma pergunta
    @Query("SELECT new com.mindtrack.entity.dto.relatorio.RespostaAbertaDTO(rq.id, rq.resposta_aberta) " +
            "FROM RespostaQuestionario rq " +
            "WHERE rq.questionario.id = :questionarioId AND rq.pergunta.id = :perguntaId AND rq.resposta_aberta IS NOT NULL")
    List<RespostaAbertaDTO> findRespostasAbertas(
            @Param("questionarioId") Long questionarioId,
            @Param("perguntaId") Long perguntaId
    );

    // NOVA CONSULTA: Calcula a média geral dos valores das perguntas LIKERT
    @Query("SELECT SUM(rq.resposta.valor) FROM RespostaQuestionario rq " +
            "WHERE rq.questionario.id = :questionarioId AND rq.pergunta.tipo = 'LIKERT'")
    Double findAverageValorByQuestionario(
            @Param("questionarioId") Long questionarioId
    );

    // NOVA CONSULTA: Calcula a média dos valores agrupada por setor
    @Query("SELECT new com.mindtrack.entity.dto.relatorio.MediaPorSetorDTO(rq.funcionario.setor, AVG(rq.resposta.valor)) " +
            "FROM RespostaQuestionario rq " +
            "WHERE rq.questionario.id = :questionarioId AND rq.pergunta.tipo = 'LIKERT' " +
            "GROUP BY rq.funcionario.setor")
    List<MediaPorSetorDTO> findAverageValorBySetor(
            @Param("questionarioId") Long questionarioId
    );

    @Query("SELECT rq FROM RespostaQuestionario rq " +
            "JOIN FETCH rq.funcionario f " +
            "JOIN FETCH rq.resposta op " +
            "WHERE rq.questionario.id = :questionarioId AND rq.pergunta.tipo = 'LIKERT'")
    List<RespostaQuestionario> findRespostasComValorByQuestionarioId(@Param("questionarioId") Long questionarioId);


}
