package com.mindtrack.controller;

import com.mindtrack.entity.SuportMaterial;
import com.mindtrack.entity.dto.SuportMaterialDTO;
import com.mindtrack.services.SuportMaterialService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    SuportMaterialService suportMaterialService;


    @PostMapping(value ="/criar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADM')")
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
    public ResponseEntity<List<SuportMaterialDTO>> listar() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(suportMaterialService.listarTodos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        try {
            return suportMaterialService.download(fileName, request);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(suportMaterialService.buscarPorId(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/editar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> editarMaterial(
            @PathVariable Long id,
            @RequestPart("dados") SuportMaterialDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            suportMaterialService.editar(id, dto, file);
            return ResponseEntity.ok("Material atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> removerMaterial(@PathVariable Long id) {
        try {
            suportMaterialService.remover(id);
            return ResponseEntity.ok("Material removido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
