package com.mindtrack.repository;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    List<CheckIn> findByFuncionarioOrderByDataHoraDesc(Funcionario funcionario);

    @Query(value = """
        SELECT f.setor AS setor,
            ROUND(AVG(c.nivel_humor)::numeric, 2) AS mediaNivelHumor
        FROM checkins c
        JOIN usuarios f ON c.id_funcionario = f.id
        WHERE f.perfil = 'Funcionário'
        AND (COALESCE(:dataInicial, '1970-01-01 00:00:00'::timestamp) IS NULL OR c.data_hora >= COALESCE(:dataInicial, '1970-01-01 00:00:00'::timestamp))
        AND (COALESCE(:dataFinal, '9999-12-31 23:59:59'::timestamp) IS NULL OR c.data_hora <= COALESCE(:dataFinal, '9999-12-31 23:59:59'::timestamp))
        GROUP BY f.setor
        ORDER BY mediaNivelHumor DESC
    """, nativeQuery = true)
    List<Object[]> obterMediaPorSetor(
        @Param("dataInicial") LocalDateTime dataInicial,
        @Param("dataFinal") LocalDateTime dataFinal
    );

    @Query(value = """
        SELECT c.nivel_humor AS nota,
            ROUND((COUNT(*) * 100.0) /
                    (SELECT COUNT(*)
                    FROM checkins
                    WHERE (COALESCE(:inicio, '1970-01-01 00:00:00'::timestamp) IS NULL OR data_hora >= COALESCE(:inicio, '1970-01-01 00:00:00'::timestamp))
                        AND (COALESCE(:fim, '9999-12-31 23:59:59'::timestamp) IS NULL OR data_hora <= COALESCE(:fim, '9999-12-31 23:59:59'::timestamp))
                    ), 2) AS percentual
        FROM checkins c
        WHERE (COALESCE(:inicio, '1970-01-01 00:00:00'::timestamp) IS NULL OR c.data_hora >= COALESCE(:inicio, '1970-01-01 00:00:00'::timestamp))
        AND (COALESCE(:fim, '9999-12-31 23:59:59'::timestamp) IS NULL OR c.data_hora <= COALESCE(:fim, '9999-12-31 23:59:59'::timestamp))
        GROUP BY c.nivel_humor
        ORDER BY nota ASC
    """, nativeQuery = true)
    List<Object[]> obterPercentualPorNota(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    @Query(value = """
        SELECT f.setor AS setor,
            ROUND((COUNT(CASE WHEN c.nivel_humor IN (1, 2) THEN 1 END) * 100.0) / COUNT(*), 2) AS percentualNegativo
        FROM checkins c
        JOIN usuarios f ON c.id_funcionario = f.id
        WHERE f.perfil = 'Funcionário'
        AND (COALESCE(:dataInicial, '1970-01-01 00:00:00'::timestamp) IS NULL OR c.data_hora >= COALESCE(:dataInicial, '1970-01-01 00:00:00'::timestamp))
        AND (COALESCE(:dataFinal, '9999-12-31 23:59:59'::timestamp) IS NULL OR c.data_hora <= COALESCE(:dataFinal, '9999-12-31 23:59:59'::timestamp))
        GROUP BY f.setor
        ORDER BY percentualNegativo DESC
    """, nativeQuery = true)
    List<Object[]> obterPercentualNegativoPorSetor(
        @Param("dataInicial") LocalDateTime dataInicial,
        @Param("dataFinal") LocalDateTime dataFinal
    );

    @Query(value = """
        SELECT f.setor AS setor,
            ROUND((COUNT(DISTINCT c.id_funcionario) * 100.0) / COUNT(DISTINCT f.id), 2) AS percentualRespondentes
        FROM usuarios f
        LEFT JOIN checkins c
        ON f.id = c.id_funcionario
        AND (COALESCE(:dataInicial, '1970-01-01 00:00:00'::timestamp) IS NULL OR c.data_hora >= COALESCE(:dataInicial, '1970-01-01 00:00:00'::timestamp))
        AND (COALESCE(:dataFinal, '9999-12-31 23:59:59'::timestamp) IS NULL OR c.data_hora <= COALESCE(:dataFinal, '9999-12-31 23:59:59'::timestamp))
        WHERE f.perfil = 'Funcionário'
        GROUP BY f.setor
        ORDER BY percentualRespondentes DESC
    """, nativeQuery = true)
    List<Object[]> obterPercentualRespondentesPorSetor(
        @Param("dataInicial") LocalDateTime dataInicial,
        @Param("dataFinal") LocalDateTime dataFinal
    );
}