package com.mindtrack.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcaoDTO {
    private int id;
    private String textoOpcao;
    private int valor;
}
