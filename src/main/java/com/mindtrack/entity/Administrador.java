package com.mindtrack.entity;


import com.mindtrack.entity.dto.CadastroDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DiscriminatorValue("Administrador")
public class Administrador extends Usuario {
    public Administrador(CadastroDTO cadastroDTO) {
        super(cadastroDTO);
    }

    public Administrador() {
        super();
    }
}
