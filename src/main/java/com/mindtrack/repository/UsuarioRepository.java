package com.mindtrack.repository;

import com.mindtrack.entity.CadastroInterface;
import com.mindtrack.entity.Usuario;
import com.mindtrack.enums.Status;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.status <> :statusParaIgnorar")
    List<Usuario> buscarTodosExcetoStatus(@Param("statusParaIgnorar") Status status);
}

