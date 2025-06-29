package com.mindtrack.repository;

import com.mindtrack.entity.Pergunta;
import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.dto.relatorio.RespostaAbertaDTO;
import com.mindtrack.entity.dto.relatorio.RespostaAgrupadaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Questionario, Long> {

    @Query("""
    SELECT s FROM Questionario s
    WHERE s.publico = true AND s.id NOT IN (
        SELECT DISTINCT rq.questionario.id
        FROM RespostaQuestionario rq
        WHERE rq.funcionario.id = :funcId)
""")
    List<Questionario> findQuestionariosNaoRespondidosPorUsuario(@Param("funcId") Long funcionarioId);






}
