package com.mindtrack.repository;

import com.mindtrack.entity.Survey;
import com.mindtrack.entity.SurveyReply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyReplayRepository extends JpaRepository<SurveyReply, Long> {
    List<SurveyReply> findBySurveyId(Long id);
    long countBySurvey(Survey survey);
}
