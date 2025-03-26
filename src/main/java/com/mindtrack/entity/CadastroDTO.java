package com.mindtrack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroDTO {

    private Long id; // ID da tabela Usuario
    private String cpf;
    private String nome;
    private String email;
    private String setor;
    private String cargo;
    private String perfil;


    // Construtor usando CadastroInterface
    public CadastroDTO(CadastroInterface cadastro) {
        this.id = cadastro.getId();
        this.cpf = cadastro.getCpf();
        this.nome = cadastro.getNome();
        this.email = cadastro.getEmail();
        this.setor = cadastro.getSetor();
        this.cargo = cadastro.getCargo();
        this.perfil = cadastro.getPerfil();
    }

    // Novo construtor para Funcionario
    public CadastroDTO(Funcionario funcionario) {
        this.id = funcionario.getUsuario().getId(); // ID do usuário relacionado
        this.cpf = funcionario.getCpf();
        this.nome = funcionario.getNome();
        this.email = funcionario.getEmail();
        this.setor = funcionario.getSetor();
        this.cargo = funcionario.getCargo();
        this.perfil = "Funcionario";
    }

    // Novo construtor para Administrador
    public CadastroDTO(Administrador administrador) {
        this.id = administrador.getUsuario().getId(); // ID do usuário relacionado
        this.cpf = administrador.getCpf();
        this.nome = administrador.getNome();
        this.email = administrador.getEmail();
        this.setor = administrador.getSetor();
        this.cargo = administrador.getCargo();
        this.perfil = "Administrador";
    }

}
