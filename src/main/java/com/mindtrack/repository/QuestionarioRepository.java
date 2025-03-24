package com.mindtrack.repository;

import com.mindtrack.entity.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {
}
