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

}
