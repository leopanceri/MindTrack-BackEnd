package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.enums.Status;
import com.mindtrack.repository.AdministradorRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAdministrador(Administrador a){
        a.setSenha(passwordEncoder.encode("abc123"));
        a.setStatus("Ativo");
        administradorRepository.save(a);
    }

    public Administrador findById(Long id) {
        return administradorRepository.findById(id).orElseThrow(()-> new RuntimeException("Administrador inválido"));
    }

    public Administrador updateAdministrador(CadastroDTO cadastroDTO){
        Administrador administrador = administradorRepository.findById(cadastroDTO.getId())
                .orElseThrow(()-> new RuntimeException("Administrador não encontrado"));
        administrador.setCpf(cadastroDTO.getCpf());
        administrador.setNome(cadastroDTO.getNome());
        administrador.setEmail(cadastroDTO.getEmail());
        administrador.setCargo(cadastroDTO.getCargo());
        administrador.setSetor(cadastroDTO.getSetor());
        return administradorRepository.save(administrador);
    }
}
