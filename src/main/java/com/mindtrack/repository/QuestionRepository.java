package com.mindtrack.repository;

import com.mindtrack.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Pergunta, Long> {

    @Query("SELECT p FROM Pergunta p LEFT JOIN FETCH p.opcoes ORDER BY p.id")
    List<Pergunta> findAllWithOpts();
}
