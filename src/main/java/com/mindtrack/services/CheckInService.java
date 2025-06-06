package com.mindtrack.services;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.entity.dto.CheckInDTO;
import com.mindtrack.repository.CheckInRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    public List<CheckIn> listaCheckInFuncionario(int idFuncionario) throws ObjectNotFoundException {
        return checkInRepository.findByFuncionarioOrderByDateTime(funcionarioService.findById((long) idFuncionario));
    }

    public void novoCheckIn (CheckInDTO checkInDTO) {
        CheckIn checkIn = new CheckIn();
        checkIn.setDateTime(LocalDateTime.now());
        checkIn.setHumorLevel(checkInDTO.getHumorLevel());
        checkIn.setComment(checkInDTO.getComment());
        checkIn.setFuncionario(funcionarioService.findById((long) checkInDTO.getIdFuncionario()));
        checkInRepository.save(checkIn);
    }

}
