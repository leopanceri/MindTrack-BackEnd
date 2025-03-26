package com.mindtrack.services;

import com.mindtrack.entity.Survey;
import com.mindtrack.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }
    public List<Survey> listaQuestionarios() {
        return surveyRepository.findAll();
    }
}
