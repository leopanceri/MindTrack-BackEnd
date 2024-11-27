package com.mindtrack.repository;

import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
