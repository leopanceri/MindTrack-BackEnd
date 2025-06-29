package com.mindtrack.controller;

import com.mindtrack.entity.dto.CheckInDTO;
import com.mindtrack.services.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
@RequestMapping("/checkin")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @GetMapping("/historico/{id}")
    @PreAuthorize("hasAuthority('FUNC')")
    public ResponseEntity<?> getCheckinFuncionario(@PathVariable int id) {
        try {
            return ResponseEntity.ok(checkInService.listaCheckInFuncionario(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/novo")
    @PreAuthorize("hasAuthority('FUNC')")
    public void novoCheckin(@RequestBody CheckInDTO checkInDTO) {
        checkInService.novoCheckIn(checkInDTO);
    }

    @GetMapping("/media-por-setor")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<List<Map<String, Object>>> obterMediaPorSetor(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(checkInService.mediaPorSetor(dataInicial, dataFinal));
    }

    @GetMapping("/percentual-por-nota")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<List<Map<String, Object>>> obterPercentualPorNota(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(checkInService.percentualPorNota(dataInicial, dataFinal));
    }

    @GetMapping("/percentual-negativo-por-setor")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<List<Map<String, Object>>> obterPercentualNegativoPorSetor(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(checkInService.percentualNegativoPorSetor(dataInicial, dataFinal));
    }

    @GetMapping("/percentual-respondentes-por-setor")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<List<Map<String, Object>>> obterPercentualRespondentesPorSetor(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(checkInService.paticipacaoPorSetor(dataInicial, dataFinal));
    }
}
