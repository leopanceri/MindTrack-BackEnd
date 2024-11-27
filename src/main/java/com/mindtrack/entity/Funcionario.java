package com.mindtrack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column (name="cpf")
    private String cpf;
    @Column (name="nome")
    private String nome;
    @Column (name="email")
    private String email;
    @Column (name="setor")
    private String setor;
    @Column (name="cargo")
    private String cargo;
    @Column (name="id_usuario")
    private Long usuarioId;

    public Funcionario() {
    }

    public Funcionario(Long id, String cpf, String nome, String email, String setor, String cargo, Long usuarioId) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.setor = setor;
        this.cargo = cargo;
        this.usuarioId = usuarioId;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
