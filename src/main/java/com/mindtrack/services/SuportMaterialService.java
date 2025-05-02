package com.mindtrack.services;

import com.mindtrack.FileStorageProperties;
import com.mindtrack.entity.SuportMaterial;
import com.mindtrack.entity.dto.SuportMaterialDTO;
import com.mindtrack.repository.SuportMaterialRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SuportMaterialService {

    private final SuportMaterialRepository suportMaterialRepository;
    private final Path fileStorageLocation;

    @Autowired
    private ModelMapper mapper;

    public SuportMaterialService(SuportMaterialRepository suportMaterialRepository, FileStorageProperties fileStorageProperties) {
        this.suportMaterialRepository = suportMaterialRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    @Transactional
    public void save(SuportMaterialDTO materialDTO, MultipartFile file) throws Exception {
        String fileName = null;
        String filePath = null;
        if(file != null && !file.isEmpty()) {
            fileName = System.currentTimeMillis() + "_" +StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            try{
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                file.transferTo(targetLocation);

                filePath = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/materiais/download/")
                        .path(fileName)
                        .toUriString();
            }catch (Exception e){
                throw new RuntimeException("Falha ao salvar arquivo!");
            }
        }
        SuportMaterial suportMaterial = new SuportMaterial();
        suportMaterial.setTitle(materialDTO.getTitle());
        suportMaterial.setContent(materialDTO.getContent());
        suportMaterial.setLinks(materialDTO.getLinks());
        suportMaterial.setFileName(fileName);
        suportMaterial.setFilePath(filePath);
        suportMaterialRepository.save(suportMaterial);
    }

    @Transactional
    public ResponseEntity<Resource> download(String fileName, HttpServletRequest request) throws Exception {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());

            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public List<SuportMaterialDTO> listarTodos() {
        List<SuportMaterial> suportMaterials = suportMaterialRepository.findAll();
        return suportMaterials.stream().map(e -> mapper.map(e, SuportMaterialDTO.class)).collect(Collectors.toList());
    }

    public SuportMaterialDTO buscarPorId(Long id) {
        SuportMaterial sm = suportMaterialRepository.findById(id).orElse(null);
        return mapper.map(sm, SuportMaterialDTO.class);
    }
}
