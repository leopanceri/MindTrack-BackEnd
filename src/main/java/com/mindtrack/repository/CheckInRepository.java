package com.mindtrack.repository;

import com.mindtrack.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckInRepository extends JpaRepository <CheckIn, Long>{
    List<CheckIn> findByIdFuncionarioOrderByDataHoraDesc(int idFuncionario);

    void deleteByIdFuncionario(int i);

}
