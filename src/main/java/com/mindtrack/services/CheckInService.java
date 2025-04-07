package com.mindtrack.services;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.entity.dto.CheckInDTO;
import com.mindtrack.repository.CheckInRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    private FuncionarioService funcionarioService;

    public List<CheckIn> findAllByFuncionario(int idFuncionario) throws ObjectNotFoundException {
        return checkInRepository.findByFuncionarioOrderByDateTime(funcionarioService.findById((long) idFuncionario));
    }

    public void crateNewCheckIn(CheckInDTO checkInDTO) {
        CheckIn checkIn = new CheckIn();
        checkIn.setDateTime(LocalDateTime.now());
        checkIn.setHumorLevel(checkInDTO.getHumorLevel());
        checkIn.setComment(checkInDTO.getComment());
        checkIn.setFuncionario(funcionarioService.findById((long) checkInDTO.getIdFuncionario()));
        checkInRepository.save(checkIn);
    }
/*
    @Transactional
    public void removerCheckIns(Long id) {
        checkInRepository.deleteByIdFuncionario(id.intValue());
    }

 */
}
