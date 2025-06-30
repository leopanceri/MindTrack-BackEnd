package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.entity.Funcionario;
import com.mindtrack.entity.Usuario;
import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.enums.Status;
import com.mindtrack.repository.UsuarioRepository;
import com.mindtrack.services.helpers.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@Service
public class UsuarioService {

    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    EmailService emailService;


    public ResponseEntity<?> cadastrarUsuario(CadastroDTO newCadastro) {
        if(usuarioRepository.existsByEmail(newCadastro.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado!");
        if (Objects.equals(newCadastro.getPerfil(), "Funcionário")) {
            Funcionario func = new Funcionario(newCadastro);
            func = funcionarioService.criarFuncionario(func);
            func.setPerfil("Funcionário");

            emailService.enviaEmailCadastro(func);


            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(func, CadastroDTO.class));

        }else {
            Administrador adm = new Administrador(newCadastro);
            adm = administradorService.criarAdministrador(adm);
            emailService.enviaEmailCadastro(adm);

            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(adm, CadastroDTO.class));
        }
    }

   public List<CadastroDTO> retornaTodosCadastrados(){
        List<Usuario> cadastroList = usuarioRepository.buscarTodosExcetoStatus(Status.INATIVO);
        return cadastroList.stream().map(e -> mapper.map(e, CadastroDTO.class)).collect(Collectors.toList());
   }

    public CadastroDTO buscaPorId(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + idUsuario);
        }
        return mapper.map(usuario.get(), CadastroDTO.class);
    }    

    public CadastroDTO editarUsuario(CadastroDTO c) {
        if(Objects.equals(c.getPerfil(), "Funcionário")) {
            return mapper.map(funcionarioService.editarFuncionario(c), CadastroDTO.class);
        } else if (Objects.equals(c.getPerfil(), "Administrador")) {
            return mapper.map(administradorService.editarAdministrador(c), CadastroDTO.class);
        }else{
            throw new RuntimeException("Perfil informado é inválido!");
        }
    }

    public void inativaUsuario(Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado com o ID: " + id));
        u.setStatus(Status.INATIVO);
        usuarioRepository.save(u);
    }

    public Usuario buscarPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        return usuario;
    }

    public void atualizaUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }


}
