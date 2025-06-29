package com.mindtrack.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CheckInDTO{
        private Long id;
        private LocalDateTime dataHora;
        private int nivelHumor;
        private String comentario;
        private int idFuncionario;
}
