package com.mindtrack.services;

import com.mindtrack.entity.SuportMaterial;
import com.mindtrack.entity.dto.SuportMaterialDTO;
import com.mindtrack.repository.SuportMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class SuportMaterialService {

    @Autowired
    private SuportMaterialRepository suportMaterialRepository;


    @Transactional
    public void save(SuportMaterialDTO materialDTO, MultipartFile file) throws Exception {
        byte[] arquivo = null;
        if(file != null && !file.isEmpty()) {
            try (InputStream is = file.getInputStream()) {
                arquivo = is.readAllBytes();
            }
        }
        SuportMaterial suportMaterial = new SuportMaterial();
        suportMaterial.setTitle(materialDTO.getTitle());
        suportMaterial.setContent(materialDTO.getContent());
        suportMaterial.setLinks(materialDTO.getLinks());
        suportMaterial.setFile(arquivo);
        suportMaterialRepository.save(suportMaterial);
    }

    @Transactional
    public byte[] download(Long id) throws Exception {
        SuportMaterial suportMaterial = suportMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("Material não encontrado"));
        if (suportMaterial.getFile() == null) {
            throw new RuntimeException("Documento não possui arquivo");
        }
        return suportMaterial.getFile();
    }

    public List<SuportMaterial> listarTodos() {
        return suportMaterialRepository.findAll();
    }
}
