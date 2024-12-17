package com.mindtrack.repository;

import com.mindtrack.entity.CadastroInterface;
import com.mindtrack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = """
            SELECT
                u.id AS id,
                COALESCE(f.nome, a.nome) AS nome,
                COALESCE(f.email, a.email) AS email,
                COALESCE(f.cpf, a.cpf) AS cpf,
                COALESCE(f.setor, a.setor) AS setor,
                COALESCE(f.cargo, a.cargo) AS cargo,
                u.perfil
            FROM usuarios u
            LEFT JOIN funcionarios f ON u.id = f.id_usuario
            LEFT JOIN administradores a ON u.id = a.id_usuario
            WHERE f.id IS NOT NULL OR a.id IS NOT null
            ORDER BY nome ASC
            """, nativeQuery = true)
    List<CadastroInterface> buscarUsuariosCadastrados();
}
