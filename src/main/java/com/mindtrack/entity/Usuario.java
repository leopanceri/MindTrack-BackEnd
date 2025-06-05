package com.mindtrack.entity;

import com.mindtrack.entity.dto.CadastroDTO;
import com.mindtrack.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="perfil", discriminatorType = DiscriminatorType.STRING)
@Table(name="usuarios")
public class Usuario {
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
    @Column (name="status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column (name="senha")
    private String senha;
    @Formula("perfil")
    private String perfil;

    public Usuario(CadastroDTO cadastroDTO) {
        this.id = cadastroDTO.getId();
        this.cpf= cadastroDTO.getCpf();
        this.nome= cadastroDTO.getNome();
        this.email= cadastroDTO.getEmail();
        this.setor= cadastroDTO.getSetor();
        this.cargo= cadastroDTO.getCargo();
    }

    public Usuario() {

    }
}
