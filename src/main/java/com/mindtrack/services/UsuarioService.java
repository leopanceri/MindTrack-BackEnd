package com.mindtrack.services;

import com.mindtrack.entity.CadastroInterface;
import com.mindtrack.entity.Usuario;
import com.mindtrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Usuario buscaUsuarioId(Long id){
        Optional<Usuario> u = usuarioRepository.findById(id);
        return u.orElse(null);
    }

    public void removeUsuario(Long id) {
        usuarioRepository.deleteUsuarioById(id);
    }

    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
