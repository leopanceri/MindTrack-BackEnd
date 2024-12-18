package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import com.mindtrack.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public void createAdministrador(Administrador a){
        administradorRepository.save(a);
    }

    @Transactional
    public void removeAdmByUsuario(Usuario usuario) {
        administradorRepository.deleteByUsuario(usuario);
    }

    public Administrador findByUsuario(Usuario u) {
        return administradorRepository.findByUsuario(u);
    }

    public Administrador updateAdministrador(Administrador administrador){
        return administradorRepository.save(administrador);
    }
}
