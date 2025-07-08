package com.mindtrack.repository;

import com.mindtrack.entity.MaterialApoio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuportMaterialRepository extends JpaRepository<MaterialApoio, Long> {

}
