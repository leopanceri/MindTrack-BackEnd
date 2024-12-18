package com.mindtrack.services;

import com.mindtrack.entity.*;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindtrack.repository.FuncionarioRepository;
import com.mindtrack.repository.AdministradorRepository;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CadastroService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private AdministradorRepository administradorRepository;


    public Usuario cadastrarUsuario(CadastroDTO cadastroDTO) {
        Usuario usuario = usuarioService.criarUsuario(cadastroDTO.getEmail(), cadastroDTO.getPerfil());
        if (Objects.equals(usuario.getPerfil(), "Funcionario")) {
            Funcionario func = new Funcionario(0L,cadastroDTO.getCpf(), cadastroDTO.getNome(), cadastroDTO.getEmail(),
                    cadastroDTO.getSetor(), cadastroDTO.getCargo(), usuario);
            funcionarioService.createFuncionario(func);

        }else{
            Administrador adm = new Administrador(0L,cadastroDTO.getCpf(), cadastroDTO.getNome(), cadastroDTO.getEmail(),
                    cadastroDTO.getSetor(), cadastroDTO.getCargo(), usuario);
            administradorService.createAdministrador(adm);
        }
        return usuario;
    }

    public List<CadastroDTO> retornaTodosCadastrados(){
        List<CadastroInterface> cadastroList = usuarioService.buscarUsuariosCadastrados();
        List<CadastroDTO> result = cadastroList.stream().map(e -> new CadastroDTO(e)).collect(Collectors.toList());
        return result;
    }

    public CadastroDTO findById(Long idUsuario) {
        // Primeiro, encontra o Usuario com o ID fornecido
        Usuario usuario = usuarioService.buscaUsuarioId(idUsuario);
        
        if (usuario == null) {
            // Se o usuário não for encontrado, retorna uma exceção ou mensagem apropriada
            throw new RuntimeException("Usuário não encontrado com o ID: " + idUsuario);
        }
        
        // Aqui é onde você verifica o perfil do usuário para determinar onde buscar os dados adicionais
        if (Objects.equals(usuario.getPerfil(), "Funcionario")) {
            // Se for "Funcionario", busca os dados na tabela "funcionarios"
            Funcionario funcionario = funcionarioRepository.findByUsuario(usuario);
            if (funcionario == null) {
                throw new RuntimeException("Funcionário não encontrado para o Usuário com ID: " + idUsuario);
            }
            return new CadastroDTO(funcionario);  // Retorna os dados do Funcionario
    
        } else if (Objects.equals(usuario.getPerfil(), "Administrador")) {
            // Se for "Administrador", busca os dados na tabela "administradores"
            Administrador administrador = administradorRepository.findByUsuario(usuario);
            if (administrador == null) {
                throw new RuntimeException("Administrador não encontrado para o Usuário com ID: " + idUsuario);
            }
            return new CadastroDTO(administrador);  // Retorna os dados do Administrador
        } else {
            // Caso o perfil do usuário não seja nem "Funcionario" nem "Administrador"
            throw new RuntimeException("Perfil inválido associado ao Usuário com ID: " + idUsuario);
        }
    }    
    

    public void atualizaCadastro(CadastroDTO c) {

    }

    public void removeCadastro(Long id) {
        Usuario u = usuarioService.buscaUsuarioId(id);
        if(u != null){
            if(Objects.equals(u.getPerfil(), "FUNCIONARIO")){
                funcionarioService.removeFuncByUsuario(u);
            }else{
                administradorService.removeAdmByUsuario(u);
            }
        }
        usuarioService.removeUsuario(id);
    }
}
