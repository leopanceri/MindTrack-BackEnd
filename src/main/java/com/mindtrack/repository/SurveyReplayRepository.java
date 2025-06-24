package com.mindtrack.repository;

import com.mindtrack.entity.Questionario;
import com.mindtrack.entity.RespostaQuestionario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyReplayRepository extends JpaRepository<RespostaQuestionario, Long> {
    List<RespostaQuestionario> findByQuestionarioId(Long id);
    long countByQuestionario(Questionario questionario);
}
