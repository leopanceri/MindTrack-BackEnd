package com.mindtrack.entity;

import com.mindtrack.enums.Category;
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


