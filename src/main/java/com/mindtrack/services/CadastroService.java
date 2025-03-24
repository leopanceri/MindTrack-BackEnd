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
        return cadastroList.stream().map(CadastroDTO::new).collect(Collectors.toList());
    }

    public CadastroDTO findById(Long idUsuario) {
        Usuario usuario = usuarioService.buscaUsuarioId(idUsuario);
        
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + idUsuario);
        }
        
        if (Objects.equals(usuario.getPerfil(), "Funcionario")) {
            Funcionario funcionario = funcionarioRepository.findByUsuario(usuario);
            if (funcionario == null) {
                throw new RuntimeException("Funcionário não encontrado para o Usuário com ID: " + idUsuario);
            }
            return new CadastroDTO(funcionario);  // Retorna os dados do Funcionario
    
        } else if (Objects.equals(usuario.getPerfil(), "Administrador")) {
            Administrador administrador = administradorRepository.findByUsuario(usuario);
            if (administrador == null) {
                throw new RuntimeException("Administrador não encontrado para o Usuário com ID: " + idUsuario);
            }
            return new CadastroDTO(administrador); 
        } else {
            throw new RuntimeException("Perfil inválido associado ao Usuário com ID: " + idUsuario);
        }
    }    
    

    public void atualizaCadastro(CadastroDTO c) {
        Usuario u = usuarioService.buscaUsuarioId(c.getId());
        if (u == null) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + c.getId());
        }else{
            u.setLogin(c.getEmail());
            usuarioService.updateUsuario(u);
        }
        if(Objects.equals(u.getPerfil(), "Funcionario")){
            Funcionario f = funcionarioService.findByUsuario(u);
            f.setNome(c.getNome());
            f.setCpf(c.getCpf());
            f.setEmail(c.getEmail());
            f.setCargo(c.getCargo());
            f.setSetor(c.getSetor());
            funcionarioService.updateFuncionario(f);
        }else{
            Administrador a = administradorService.findByUsuario(u);
            a.setNome(c.getNome());
            a.setCpf(c.getCpf());
            a.setEmail(c.getEmail());
            a.setCargo(c.getCargo());
            a.setSetor(c.getSetor());
            administradorService.updateAdministrador(a);
        }
    }

    public void removeCadastro(Long id) {
        Usuario u = usuarioService.buscaUsuarioId(id);
        if(u != null){
            if(Objects.equals(u.getPerfil(), "Funcionario")){
                funcionarioService.removeFuncByUsuario(u);
            }else{
                administradorService.removeAdmByUsuario(u);
            }
        }
        usuarioService.removeUsuario(id);
    }
}
