package com.techpixe.ehr.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.techpixe.ehr.dto.AnswerDto;
import com.techpixe.ehr.dto.ExamResultDto;
import com.techpixe.ehr.entity.Interview;

public interface InterviewService {

	void sendInterviewDetails(Long candidateId, Interview interviewDetails);

	void updateInterviewByCandidateId(Long candidate_Id, LocalDate interviewDate, LocalTime interviewTime);

	void saveAnswers(Long candidateId, ExamResultDto answers);

}
