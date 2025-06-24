package com.mindtrack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="perguntas")
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="id_tema", nullable=false)
    private TemaPergunta tema;
    @Column (nullable=false, length = 25)
    private String tipo;
    @Column (nullable=false)
    private String texto;
    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcaoResposta> opcoes = new ArrayList<>();

    /*
    public Pergunta(Long questionId) {
        this.id = questionId;
    }
     */

    public Pergunta(TemaPergunta tema, String texto, String tipo) {
        this.tema = tema;
        this.texto = texto;
        this.tipo = tipo;
    }
}
