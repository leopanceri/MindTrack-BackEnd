package com.mindtrack.entity.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerguntaDTO {

    private Long id;
    private Long idTema;
    private String tipo;
    private String texto;
    private List<OpcaoDTO> opcoes;

    /*
    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.category = question.getCategory().getValue();
        this.tipo = question.getTipo();
        this.text = question.getText();
        this.opts = question.getOpcoes();
    }
     */
}


