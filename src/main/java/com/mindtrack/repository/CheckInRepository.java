package com.mindtrack.repository;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckInRepository extends JpaRepository <CheckIn, Long>{
    List<CheckIn> findByFuncionarioOrderByDateTime(Funcionario funcionario);

    //void deleteByIdFuncionario(int i);

    @Query(
        value = "SELECT f.setor AS setor, ROUND(AVG(c.nivel_humor)::numeric, 2) AS mediaNivelHumor " +
                "FROM checkins c " +
                "JOIN usuarios f ON c.id_funcionario = f.id " +
                "WHERE f.perfil = 'Funcionário' " +
                "GROUP BY f.setor " +
                "ORDER BY mediaNivelHumor DESC",
        nativeQuery = true)
        List<Object[]> obterMediaPorSetor();

    @Query(
        value = "SELECT c.nivel_humor AS nota, COUNT(*) AS quantidade " +
                "FROM checkins c " +
                "GROUP BY c.nivel_humor " +
                "ORDER BY nota ASC",
        nativeQuery = true)
        List<Object[]> obterQuantidadePorNota();

    @Query(
        value = "SELECT f.setor AS setor, " +
                "ROUND( (COUNT(CASE WHEN c.nivel_humor IN (1,2) THEN 1 END) * 100.0) / COUNT(*), 2) AS percentualNegativo " +
                "FROM checkins c " +
                "JOIN usuarios f ON c.id_funcionario = f.id " +
                "WHERE f.perfil = 'Funcionário' " +
                "GROUP BY f.setor " +
                "ORDER BY percentualNegativo DESC",
        nativeQuery = true)
        List<Object[]> obterPercentualNegativoPorSetor();

    @Query(
        value = "SELECT f.setor AS setor, " +
                "ROUND( (COUNT(DISTINCT c.id_funcionario) * 100.0) / COUNT(DISTINCT f.id), 2) AS percentualRespondentes " +
                "FROM usuarios f " +
                "LEFT JOIN checkins c ON f.id = c.id_funcionario " +
                "WHERE f.perfil = 'Funcionário' " +
                "GROUP BY f.setor " +
                "ORDER BY percentualRespondentes DESC",
        nativeQuery = true)
        List<Object[]> obterPercentualRespondentesPorSetor();
}
