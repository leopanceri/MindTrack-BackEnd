package com.mindtrack.services;

import com.mindtrack.entity.*;

import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    //@Autowired
    //private UsuarioService usuarioService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private AdministradorService administradorService;

    //@Autowired
    //private FuncionarioRepository funcionarioRepository;

   //@Autowired
   // AdministradorRepository administradorRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public CadastroDTO cadastrarUsuario(CadastroDTO newCadastro) {
        //Usuario usuario = usuarioService.criarUsuario(cadastroDTO.getEmail(), cadastroDTO.getPerfil());
        if (Objects.equals(newCadastro.getPerfil(), "Funcionario")) {
            Funcionario func = new Funcionario(newCadastro);
            funcionarioService.createFuncionario(func);
            return mapper.map(func, CadastroDTO.class);

        }else {
            Administrador adm = new Administrador(newCadastro);
            administradorService.createAdministrador(adm);
            return mapper.map(adm, CadastroDTO.class);
        }
    }

   public List<CadastroDTO> retornaTodosCadastrados(){
        List<Usuario> cadastroList = usuarioRepository.findAll();
        return cadastroList.stream().map(e -> mapper.map(e, CadastroDTO.class)).collect(Collectors.toList());
       //List<CadastroInterface> cadastroList = usuarioRepository.buscarUsuariosCadastrados();
       //return cadastroList.stream().map(CadastroDTO::new).collect(Collectors.toList());
   }


    public CadastroDTO findById(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + idUsuario);
        }
        return mapper.map(usuario.get(), CadastroDTO.class);
    }    
    

    public void atualizaCadastro(CadastroDTO c) {
        if(Objects.equals(c.getPerfil(), "Funcionário")) {
            funcionarioService.updateFuncionario(c);
        } else if (Objects.equals(c.getPerfil(), "Administrador")) {
            administradorService.updateAdministrador(c);
        }else{
            throw new RuntimeException("Perfil informado é inválido!");
        }
    }

    public void inativaCadastro(Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado com o ID: " + id));
        u.setStatus("INATIVO");
        usuarioRepository.save(u);
    }
}
