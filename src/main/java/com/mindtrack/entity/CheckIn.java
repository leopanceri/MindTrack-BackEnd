package com.mindtrack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "checkins")
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column (name="data_hora")
    private LocalDateTime dataHora;
    @Column (name="nivel_humor")
    private int nivelHumor;
    @Column (name="comentario")
    private String comentario;
    @Column (name="id_funcionario")
    private int idFuncionario;

    public CheckIn() {
    }

    public CheckIn(Long id, LocalDateTime dataHora, int nivelHumor, String comentario, int idFuncionario) {
        this.id = id;
        this.dataHora = dataHora;
        this.nivelHumor = nivelHumor;
        this.comentario = comentario;
        this.idFuncionario = idFuncionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckInData() {
        return dataHora;
    }

    public void setCheckInData(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getNivelHumor() {
        return nivelHumor;
    }

    public void setNivelHumor(int nivelHumor) {
        this.nivelHumor = nivelHumor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int funcionarioId) {
        this.idFuncionario = funcionarioId;
    }
}
