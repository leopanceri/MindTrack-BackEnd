package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public void createAdministrador(Administrador a){
        administradorRepository.save(a);
    }
}
