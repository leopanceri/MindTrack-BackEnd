package com.mindtrack.repository;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    //Administrador findByUsuario(Usuario u);

   // void deleteByUsuario(Usuario u);
}
