package com.mindtrack.entity.dto;

import com.mindtrack.entity.Question;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long id;
    private String category;
    private String text;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.category = question.getCategory().getValue();
        this.text = question.getText();
    }
}


