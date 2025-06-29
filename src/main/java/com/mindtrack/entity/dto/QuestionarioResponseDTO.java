package com.mindtrack.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionarioResponseDTO {
    private Long id;
    private LocalDate dataPublicacao;
    private LocalDate dataValidade;
    private String titulo;
    private String descricao;
    private boolean publico;
    private List<PerguntaDTO> perguntas;
}
