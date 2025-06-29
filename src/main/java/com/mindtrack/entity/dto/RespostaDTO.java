package com.mindtrack.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {

    private Long perguntaId;
    private Long opcaoId;
    private String texto;
}
