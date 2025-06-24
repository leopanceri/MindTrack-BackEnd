package com.mindtrack.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "respostas_questionarios")
public class RespostaQuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_questionario", nullable = false)
    private Questionario questionario;

    @ManyToOne
    @JoinColumn(name = "id_pergunta", nullable = false)
    private Pergunta pergunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_op_resposta", nullable = true)
    private OpcaoResposta resposta;

    private String resposta_aberta;

}
