package com.mindtrack.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TemaPergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=50)
    private String tema;

}
