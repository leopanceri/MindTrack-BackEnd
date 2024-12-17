package com.mindtrack.services;

import com.mindtrack.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CadastroService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private AdministradorService administradorService;


    public Usuario cadastrarUsuario(CadastroDTO cadastroDTO) {
        Usuario usuario = usuarioService.criarUsuario(cadastroDTO.getEmail(), cadastroDTO.getPerfil());
        if (Objects.equals(usuario.getPerfil(), "FUNCIONARIO")) {
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
}
