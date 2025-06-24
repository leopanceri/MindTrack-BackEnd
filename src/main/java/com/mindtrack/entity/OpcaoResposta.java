package com.mindtrack.entity;

import com.mindtrack.entity.dto.OpcaoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="opcao_resposta")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpcaoResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoOpcao;
    private int valor;

    // Muitas opções pertencem a uma pergunta.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    public OpcaoResposta(OpcaoDTO opcaoDTO, Pergunta pergunta) {
        this.textoOpcao = opcaoDTO.getTextoOpcao();
        this.valor = opcaoDTO.getValor();
        this.pergunta = pergunta;
    }

    public OpcaoResposta(Long opcaoId) {
        this.id = opcaoId;
    }
}
