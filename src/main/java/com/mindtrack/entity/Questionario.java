package com.mindtrack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="questionarios")
public class Questionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column(name= "data_publi")
    private LocalDate dataPublicacao;
    @Column(name= "data_validade")
    private LocalDate dataValidade;
    @Column(name= "titulo")
    private String titulo;
    @Column(name= "descricao")
    private String descricao;
    @Column(name = "publico")
    private boolean publico;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name= "quest_perguntas", joinColumns = @JoinColumn(name = "id_questionario"),
                inverseJoinColumns = @JoinColumn(name = "id_pergunta" ))
    private List<Pergunta> perguntas;
    @ManyToOne
    @JoinColumn(name = "id_administrador")
    private Administrador responsavel;

}
