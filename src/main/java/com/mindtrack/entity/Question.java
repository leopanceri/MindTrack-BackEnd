package com.mindtrack.entity;

import com.mindtrack.enums.Category;
import com.mindtrack.enums.converters.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="perguntas")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;
    @Column (name= "categoria")
    @Convert(converter = CategoryConverter.class)
    private Category category;
    @Column (name="texto")
    private String text;

    public Question(Long questionId) {
        this.id = questionId;
    }
}
