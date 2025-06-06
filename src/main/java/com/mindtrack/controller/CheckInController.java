package com.mindtrack.controller;

import com.mindtrack.entity.dto.CheckInDTO;
import com.mindtrack.services.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
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

}
