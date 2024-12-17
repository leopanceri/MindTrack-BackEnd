package com.mindtrack.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador {
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name="id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    public Administrador() {
    }

    public Administrador(Long id, String cpf, String nome, String email, String setor, String cargo, Usuario usuario) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.setor = setor;
        this.cargo = cargo;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
