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
    @Column (name="data_hora", nullable=false)
    private LocalDateTime dataHora;
    @Column (name="nivel_humor", nullable=false)
    private int nivelHumor;
    @Column (name="comentario")
    private String comentario;
    @ManyToOne
    @JoinColumn(name="id_funcionario", nullable=false)
    private Funcionario funcionario;

    public CheckIn() {
    }

    public CheckIn(Long id, LocalDateTime dataHora, int nivelHumor, String comentario, Funcionario funcionario) {
        this.id = id;
        this.dataHora = dataHora;
        this.nivelHumor = nivelHumor;
        this.comentario = comentario;
        this.funcionario = funcionario;
    }

}
