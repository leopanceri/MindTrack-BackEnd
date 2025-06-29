package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.enums.Status;
import com.mindtrack.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Administrador criarAdministrador(Administrador a){
        a.setSenha(passwordEncoder.encode("abc123"));
        a.setStatus(Status.ATIVO);
        return administradorRepository.save(a);
    }

    public Administrador buscaPorId(Long id) {
        return administradorRepository.findById(id).orElseThrow(()-> new RuntimeException("Administrador inválido"));
    }

    public Administrador editarAdministrador(CadastroDTO cadastroDTO){
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
