package com.mindtrack.entity;

import com.mindtrack.entity.dto.CadastroDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DiscriminatorValue("Funcionário")
public class Funcionario extends Usuario {

    public Funcionario(CadastroDTO cadastroDTO) {
        super(cadastroDTO);
    }

    public Funcionario() {
        super();
    }
}
