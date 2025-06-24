package com.mindtrack.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionarioDTO {
    private LocalDate dataPublicacao;
    private LocalDate dataValidade;
    private String titulo;
    private String descricao;
    private List<Long> perguntasId;
    private int admId;
    private boolean publico;
}


