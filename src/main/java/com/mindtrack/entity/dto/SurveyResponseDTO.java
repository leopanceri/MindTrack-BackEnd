package com.mindtrack.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponseDTO {
    private Long id;
    private LocalDate publicationDate;
    private LocalDate dueDate;
    private String title;
    private String description;
    private boolean isPublic;
    private List<QuestionDTO> questions;
    private CadastroDTO publisher;
}
