package com.techpixe.ehr.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.ehr.dto.ExamResultDto;
import com.techpixe.ehr.entity.PersonalInformation;
import com.techpixe.ehr.entity.Question;

public interface PersonalInformationService {


//	PersonalInformation addPersonalInformation(Long candidateId, String name, String mobileNumber, String emailID,
//			List<Question> questions);

	PersonalInformation savePersonalInformation(PersonalInformation personalInfo);

	void saveQuestions(Long candidate_Id, List<Question> questions, PersonalInformation savedPersonalInfo);

	PersonalInformation getPersonalInformationByCandidateId(Long candidate_Id);

	List<PersonalInformation> getAllPersonalInformation();

	PersonalInformation savePersonalInformation(MultipartFile file,
			 Long user_Id) throws Exception;
	
	
	ResponseEntity<String> savePersonalDateInformation(LocalDate date, LocalTime fromTime,String jobRole,
			 Long user_Id) throws Exception;

	void saveAnswers(Long candidateId, ExamResultDto answers);

	boolean isVerified(String email);

	PersonalInformation getPersonalInformationById(String emailid);

	List<Question> getAllQuestionsByPersonalInformationId(Long personalInformationId);

}
