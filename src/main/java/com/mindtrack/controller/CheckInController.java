package com.mindtrack.controller;

import com.mindtrack.entity.dto.CheckInDTO;
import com.mindtrack.services.CheckInService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/checkin")
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    @GetMapping("/historico/{id}")
    @PreAuthorize("hasAuthority('FUNC')")
    public ResponseEntity<?> getCheckinFuncionario(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(checkInService.listaCheckInFuncionario(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/checkin")
    @PreAuthorize("hasAuthority('FUNC')")
    public void novoCheckin(@RequestBody CheckInDTO checkInDTO) {
        checkInService.novoCheckIn(checkInDTO);
    }
    
    @GetMapping("/media-por-setor")
    public ResponseEntity<List<Map<String, Object>>> obterMediaPorSetor() {
        List<Map<String, Object>> medias = checkInService.obterMediaPorSetor();
        return ResponseEntity.ok(medias);
    }

    @GetMapping("/quantidade-por-nota")
    public ResponseEntity<List<Map<String, Object>>> obterQuantidadePorNota() {
        List<Map<String, Object>> dados = checkInService.obterQuantidadePorNota();
        return ResponseEntity.ok(dados);
    }

    @GetMapping("/percentual-negativo-por-setor")
    public ResponseEntity<List<Map<String, Object>>> obterPercentualNegativoPorSetor() {
        return ResponseEntity.ok(checkInService.obterPercentualNegativoPorSetor());
    }

    @GetMapping("/percentual-respondentes-por-setor")
    public ResponseEntity<List<Map<String, Object>>> obterPercentualRespondentesPorSetor() {
        return ResponseEntity.ok(checkInService.obterPercentualRespondentesPorSetor());
    }
}
