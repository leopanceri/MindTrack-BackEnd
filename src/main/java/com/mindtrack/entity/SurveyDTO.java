package com.mindtrack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDTO {

    private LocalDate publicationDate;
    private LocalDate dueDate;
    private String title;
    private String description;
    private List<Long> questionsId;
}
