package com.mindtrack.entity;

import com.mindtrack.enums.Category;
import com.mindtrack.enums.converters.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @Column (name="tipo")
    private String tipo;
    @Column (name="texto")
    private String text;
    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcao> opcoes = new ArrayList<>();

    public Question(Long questionId) {
        this.id = questionId;
    }

    public Question(Category category, String text, String tipo) {
        this.category = category;
        this.text = text;
        this.tipo = tipo;
    }
}
