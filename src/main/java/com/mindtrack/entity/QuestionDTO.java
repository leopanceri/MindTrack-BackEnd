package com.mindtrack.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long id;
    private String texto;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.texto = question.getText();
    }
}


