package com.mindtrack.repository;

import com.mindtrack.entity.OpcaoResposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcaoRespostaRepository extends JpaRepository<OpcaoResposta, Long> {
    List<OpcaoResposta> findByPerguntaId(Long perguntaId);
}
