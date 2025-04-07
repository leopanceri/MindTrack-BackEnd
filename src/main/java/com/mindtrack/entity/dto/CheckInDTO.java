package com.mindtrack.entity.dto;

import com.mindtrack.entity.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CheckInDTO{
        private Long id;
        private LocalDateTime dateTime;
        private int humorLevel;
        private String comment;
        private int idFuncionario;
}
