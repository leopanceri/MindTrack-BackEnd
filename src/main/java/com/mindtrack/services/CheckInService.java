package com.mindtrack.services;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.repository.CheckInRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public List<CheckIn> findAllByFuncionario(int idFuncionario) throws ObjectNotFoundException {
        return checkInRepository.findByIdFuncionarioOrderByDataHoraDesc(idFuncionario);
    }

    public void crateNewCheckIn(CheckIn checkIn) {
        checkIn.setCheckInData(LocalDateTime.now());
        checkInRepository.save(checkIn);
    }
}
