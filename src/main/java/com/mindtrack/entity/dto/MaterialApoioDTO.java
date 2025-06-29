package com.mindtrack.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialApoioDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private List<String> links;
    private String nomeArquivo;
    private String filePath;
}
