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
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column(name= "data_publi")
    private LocalDate publicationDate;
    @Column(name= "data_validade")
    private LocalDate dueDate;
    @Column(name= "titulo")
    private String title;
    @Column(name= "descricao")
    private String description;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name= "quest_perguntas", joinColumns = @JoinColumn(name = "id_questionario"),
                inverseJoinColumns = @JoinColumn(name = "id_pergunta" ))
    private List<Question> questions;
    @ManyToOne
    @JoinColumn(name = "id_administrador")
    private Administrador publisher;

}
