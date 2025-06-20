package com.mindtrack.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtrack.services.RelatorioService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/questionario/{id}")
    public ResponseEntity<?> getRelatorioQuestionario(@PathVariable Long id) {
        try {
            Map<String, Object> relatorio = relatorioService.gerarRelatorioQuestionario(id);
            return ResponseEntity.ok(relatorio);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
