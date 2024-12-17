package com.mindtrack.entity;

import jakarta.persistence.*;

public class CadastroDTO {

    private Long id; // vem id da tabela usuario
    private String cpf;
    private String nome;
    private String email;
    private String setor;
    private String cargo;
    private String perfil;

    public CadastroDTO(Long id, String cpf, String nome, String email, String setor, String cargo, String perfil) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.setor = setor;
        this.cargo = cargo;
        this.perfil = perfil;
    }

    public CadastroDTO(CadastroInterface cadastro) {
        this.id = cadastro.getId();
        this.cpf = cadastro.getCpf();
        this.nome = cadastro.getNome();
        this.email = cadastro.getEmail();
        this.setor = cadastro.getSetor();
        this.cargo = cadastro.getCargo();
        this.perfil = cadastro.getPerfil();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
