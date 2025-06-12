package com.mindtrack.entity.dto;

import com.mindtrack.entity.Question;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long id;
    private String category;
    private String tipo;
    private String text;
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


