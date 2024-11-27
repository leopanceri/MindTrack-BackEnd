package com.mindtrack.repository;

import com.mindtrack.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckInRepository extends JpaRepository <CheckIn, Long>{
    List<CheckIn> findByIdFuncionario(int idFuncionario);
}
