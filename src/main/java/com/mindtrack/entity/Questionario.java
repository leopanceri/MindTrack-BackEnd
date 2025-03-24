package com.mindtrack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Column(name= "datapubli")
    private LocalDate dataPubli;
    @Column(name= "dataval")
    private LocalDate dataVal;
    @Column(name= "titulo")
    private String titulo;
    @Column(name= "descricao")
    private String descricao;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name= "quest_perguntas", joinColumns = @JoinColumn(name = "id_questionario"),
                inverseJoinColumns = @JoinColumn(name = "id_pergunta" ))
    private List<Pergunta> perguntas;


}
