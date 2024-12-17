package com.mindtrack.services;

import com.mindtrack.entity.CadastroDTO;
import com.mindtrack.entity.CadastroInterface;
import com.mindtrack.entity.Usuario;
import com.mindtrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(String login, String perfil){
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setPerfil(perfil);
        usuario.setSenha("123456");
        return usuarioRepository.save(usuario);
    }

    public List<CadastroInterface> buscarUsuariosCadastrados(){
        return usuarioRepository.buscarUsuariosCadastrados();
    }
}
