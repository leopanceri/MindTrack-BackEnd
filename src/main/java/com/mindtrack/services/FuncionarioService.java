package com.mindtrack.services;

import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import com.mindtrack.repository.FuncionarioRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public Funcionario findById(Long id) throws ObjectNotFoundException {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario.orElseThrow();
    }

    public List<Funcionario> findAll(){
        return funcionarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void createFuncionario(Funcionario f){
        funcionarioRepository.save(f);
    }

    public Funcionario updateFuncionario(Long id, Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }

    public void deleteFuncionario(Long id){
        funcionarioRepository.deleteById(id);
    }
}
