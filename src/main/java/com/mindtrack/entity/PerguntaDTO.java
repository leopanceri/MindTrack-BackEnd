package com.mindtrack.entity;

import lombok.*;

@Getter
@Setter
public class PerguntaDTO {

    private Long id;
    private String texto;

    public PerguntaDTO() {}

    public PerguntaDTO(Long id, String texto) {
        this.id = id;
        this.texto = texto;
    }
}


