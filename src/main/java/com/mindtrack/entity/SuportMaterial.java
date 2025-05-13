package com.mindtrack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="material_apoio")
public class SuportMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="titulo")
    private String title;
    @Column(name="conteudo")
    private String content;
    @ElementCollection
    @JoinTable(name="material_links",
            joinColumns = @JoinColumn(name = "material_id"))
    @Column(name="links")
    private List<String> links;
    @Column(name = "nome_arquivo")
    private String fileName;
    @Column(name="local_arquivo")
    private String filePath;

}
