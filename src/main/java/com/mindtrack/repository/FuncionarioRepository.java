package com.mindtrack.repository;

import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    public List<Funcionario> findByNomeContaining(String nome);

    //public Funcionario findByUsuario(Usuario u);

}
