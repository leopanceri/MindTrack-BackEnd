package com.mindtrack.services;

import com.mindtrack.entity.Administrador;
import com.mindtrack.services.helpers.FileStorageProperties;
import com.mindtrack.entity.MaterialApoio;
import com.mindtrack.entity.dto.MaterialApoioDTO;
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
import java.util.stream.Collectors;

@Service
public class SuportMaterialService {

    private final SuportMaterialRepository suportMaterialRepository;
    private final Path fileStorageLocation;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AdministradorService administradorService;

    public SuportMaterialService(SuportMaterialRepository suportMaterialRepository, FileStorageProperties fileStorageProperties) {
        this.suportMaterialRepository = suportMaterialRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    @Transactional
    public void criarMaterial(MaterialApoioDTO materialDTO, MultipartFile file) throws Exception {
        String fileName = null;
        String filePath = null;
        Administrador adm = administradorService.buscaPorId(materialDTO.getIdAdmin());
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
        MaterialApoio materialApoio = new MaterialApoio();
        materialApoio.setTitulo(materialDTO.getTitulo());
        materialApoio.setConteudo(materialDTO.getConteudo());
        materialApoio.setLinks(materialDTO.getLinks());
        materialApoio.setNomeArquivo(fileName);
        materialApoio.setResponsavel(adm);
        suportMaterialRepository.save(materialApoio);
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

    public List<MaterialApoioDTO> listarTodos() {
        List<MaterialApoio> materialApoios = suportMaterialRepository.findAll();
        return materialApoios.stream().map(e -> mapper.map(e, MaterialApoioDTO.class)).collect(Collectors.toList());
    }

    public MaterialApoioDTO buscarPorId(Long id) {
        MaterialApoio sm = suportMaterialRepository.findById(id).orElse(null);
        return mapper.map(sm, MaterialApoioDTO.class);
    }

    @Transactional
    public void editar(Long id, MaterialApoioDTO dto, MultipartFile file) throws Exception {
        MaterialApoio materialExistente = suportMaterialRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Material com ID " + id + " não encontrado"));

        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);
            String filePath = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/materiais/download/")
                    .path(fileName)
                    .toUriString();
            materialExistente.setNomeArquivo(fileName);
        }

        materialExistente.setTitulo(dto.getTitulo());
        materialExistente.setConteudo(dto.getConteudo());
        materialExistente.setLinks(dto.getLinks());

        suportMaterialRepository.save(materialExistente);
    }

    @Transactional
    public void remover(Long id) {
        MaterialApoio material = suportMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material com ID " + id + " não encontrado"));
        suportMaterialRepository.delete(material);
    }
}
