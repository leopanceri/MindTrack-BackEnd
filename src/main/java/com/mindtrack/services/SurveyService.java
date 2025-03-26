package com.mindtrack.services;

import com.mindtrack.entity.Survey;
import com.mindtrack.entity.SurveyDTO;
import com.mindtrack.repository.SurveyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ModelMapper surveyMapper;


    public List<SurveyDTO> listaQuestionarios() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream().map(e-> surveyMapper.map(e, SurveyDTO.class)).collect(Collectors.toList());
    }
}
