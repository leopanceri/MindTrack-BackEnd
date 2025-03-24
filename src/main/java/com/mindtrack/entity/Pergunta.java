package com.mindtrack.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name ="perguntas")
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column (name="texto")
    private String texto;

    public Pergunta() {
    }

    public Pergunta(Long id, String texto) {
        this.id = id;
        this.texto = texto;
    }
}
