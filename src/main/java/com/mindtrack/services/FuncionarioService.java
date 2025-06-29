package com.mindtrack.services;

import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.enums.Status;
import com.mindtrack.repository.FuncionarioRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Funcionario buscaPorId(Long id) throws ObjectNotFoundException {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario.orElseThrow();
    }

    public List<Funcionario> buscarTodos(){
        return funcionarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Funcionario criarFuncionario(Funcionario f) {
        f.setStatus(Status.ATIVO);
        f.setSenha(passwordEncoder.encode("123456"));
        return funcionarioRepository.save(f);
    }

    public Funcionario editarFuncionario(CadastroDTO cadastroDto) {
        Funcionario funcionario = funcionarioRepository.findById(cadastroDto.getId()).orElseThrow(()->new RuntimeException("Funcionário não encontrado"));
        funcionario.setCpf(cadastroDto.getCpf());
        funcionario.setNome(cadastroDto.getNome());
        funcionario.setEmail(cadastroDto.getEmail());
        funcionario.setSetor(cadastroDto.getSetor());
        funcionario.setCargo(cadastroDto.getCargo());
        return funcionarioRepository.save(funcionario);
    }

}
