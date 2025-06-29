package com.mindtrack.services;

import com.mindtrack.entity.CheckIn;
import com.mindtrack.entity.dto.CheckInDTO;
import com.mindtrack.repository.CheckInRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    public List<CheckIn> listaCheckInFuncionario(int idFuncionario) throws ObjectNotFoundException {
        return checkInRepository.findByFuncionarioOrderByDateTime(
                funcionarioService.findById((long) idFuncionario)
        );
    }

    public void novoCheckIn(CheckInDTO checkInDTO) {
        CheckIn checkIn = new CheckIn();
        checkIn.setDateTime(LocalDateTime.now());
        checkIn.setHumorLevel(checkInDTO.getHumorLevel());
        checkIn.setComment(checkInDTO.getComment());
        checkIn.setFuncionario(funcionarioService.findById((long) checkInDTO.getIdFuncionario()));
        checkInRepository.save(checkIn);
    }

    public List<Map<String, Object>> mediaPorSetor(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Object[]> resultados = checkInRepository.obterMediaPorSetor(dataInicial, dataFinal);

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("setor", row[0]);
            map.put("mediaNivelHumor", row[1]);
            lista.add(map);
        }
        return lista;
    }

    public List<Map<String, Object>> percentualPorNota(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Object[]> resultados = checkInRepository.obterPercentualPorNota(dataInicial, dataFinal);

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("nota", row[0]);
            map.put("quantidade", row[1]);
            lista.add(map);
        }
        return lista;
    }

    public List<Map<String, Object>> percentualNegativoPorSetor(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Object[]> resultados = checkInRepository.obterPercentualNegativoPorSetor(dataInicial, dataFinal);

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Object[] resultado : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("setor", resultado[0]);
            item.put("percentual", resultado[1]);
            lista.add(item);
        }
        return lista;
    }

    public List<Map<String, Object>> paticipacaoPorSetor(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        List<Object[]> resultados = checkInRepository.obterPercentualRespondentesPorSetor(dataInicial, dataFinal);

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Object[] resultado : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("setor", resultado[0]);
            item.put("percentual", resultado[1]);
            lista.add(item);
        }
        return lista;
    }
}