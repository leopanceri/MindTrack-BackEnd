package com.mindtrack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="material_apoio")
public class MaterialApoio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="titulo", nullable=false)
    private String titulo;
    @Lob
    @Column(name="conteudo", nullable = false, columnDefinition = "TEXT")
    private String conteudo;
    @ElementCollection
    @JoinTable(name="material_links",
            joinColumns = @JoinColumn(name = "material_id"))
    @Column(name="links")
    private List<String> links;
    @Column(name = "nome_arquivo")
    private String nomeArquivo;

}
