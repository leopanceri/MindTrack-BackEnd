package com.mindtrack.controller;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.services.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    @GetMapping("/historico/{id}")
    public ResponseEntity<?> getCheckinFuncionario(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(checkInService.findAllByFuncionario(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/checkin")
    public void novoCheckin(@RequestBody CheckIn checkIn) {
        checkInService.crateNewCheckIn(checkIn);
    }
}
