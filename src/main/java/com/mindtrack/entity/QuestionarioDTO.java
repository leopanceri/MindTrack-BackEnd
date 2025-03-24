package com.mindtrack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionarioDTO {

    private LocalDate dataPubli;
    private LocalDate dataVal;
    private String titulo;
    private String descricao;
    private List<Long> perguntasIds;
}
