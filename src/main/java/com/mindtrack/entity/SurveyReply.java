package com.mindtrack.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "respostas_questionarios")
public class SurveyReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_questionario", nullable = false)
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "id_pergunta", nullable = false)
    private Question question;

    @Column(name="resposta", nullable = false)
    private Integer reply;

}
