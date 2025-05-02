package com.mindtrack.controller;

import com.mindtrack.entity.SuportMaterial;
import com.mindtrack.entity.dto.SuportMaterialDTO;
import com.mindtrack.services.SuportMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    SuportMaterialService suportMaterialService;

    @PostMapping(value ="/criar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criaMaterialApoio(@RequestPart("dados") SuportMaterialDTO dto,
                                               @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            suportMaterialService.save(dto, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Material criado com sucesso!");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<SuportMaterial>> listar() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(suportMaterialService.listarTodos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{id}/arquivo")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws Exception {
        byte[] dados = suportMaterialService.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(dados);
    }
}
