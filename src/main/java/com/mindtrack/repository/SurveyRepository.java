package com.mindtrack.repository;

import com.mindtrack.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("""
    SELECT s FROM Survey s
    WHERE s.id NOT IN (
        SELECT DISTINCT sr.survey.id
        FROM SurveyReply sr
        WHERE sr.funcionario.id = :funcId)
""")
    List<Survey> findQuestionariosNaoRespondidosPorUsuario(@Param("funcId") Long funcionarioId);
}
