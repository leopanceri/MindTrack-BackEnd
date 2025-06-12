package com.mindtrack.repository;

import com.mindtrack.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT p FROM Question p JOIN FETCH p.opcoes ")
    List<Question> findAllWithOpts();
}
