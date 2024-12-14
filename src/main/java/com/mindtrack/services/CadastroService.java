package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.CadastroDTO;
import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CadastroService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    AdministradorService administradorService;

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
}
