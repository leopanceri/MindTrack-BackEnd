package com.mindtrack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "checkins")
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column (name="data_hora")
    private LocalDateTime dateTime;
    @Column (name="nivel_humor")
    private int humorLevel;
    @Column (name="comentario")
    private String comment;
    @ManyToOne
    @JoinColumn(name="id_funcionario")
    private Funcionario funcionario;

    public CheckIn() {
    }

    public CheckIn(Long id, LocalDateTime dateTime, int humorLevel, String comment, Funcionario funcionario) {
        this.id = id;
        this.dateTime = dateTime;
        this.humorLevel = humorLevel;
        this.comment = comment;
        this.funcionario = funcionario;
    }

}
