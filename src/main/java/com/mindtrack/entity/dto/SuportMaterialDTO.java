package com.mindtrack.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuportMaterialDTO {
    private String title;
    private String content;
    private List<String> links;
    private String fileName;
    private String filePath;
}
