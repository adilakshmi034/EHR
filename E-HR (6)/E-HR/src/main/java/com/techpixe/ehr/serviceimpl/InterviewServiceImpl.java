package com.techpixe.ehr.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.ehr.dto.ExamResultDto;
import com.techpixe.ehr.entity.Interview;
import com.techpixe.ehr.entity.PersonalInformation;
import com.techpixe.ehr.entity.Question;
import com.techpixe.ehr.repository.InterviewRepository;
import com.techpixe.ehr.repository.PersonalInformationRepository;
import com.techpixe.ehr.repository.QuestionRepository;
import com.techpixe.ehr.service.EmailService;
import com.techpixe.ehr.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private PersonalInformationRepository personalInformationRepository;

	@Autowired
	private EmailService emailService;


	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private InterviewRepository interviewRepository;

	@Override
	public void sendInterviewDetails(Long candidateId, Interview interviewDetails) {
		PersonalInformation candidate = personalInformationRepository.findById(candidateId)
				.orElseThrow(() -> new RuntimeException("Candidate not found"));

		int totalDurationInSeconds = 0;
		List<Question> questions = candidate.getQuestions(); // Assuming you have a method to get questions
		for (Question question : questions) {
			totalDurationInSeconds += question.getMinimumTime();
		}
		LocalTime interviewStartTime = interviewDetails.getInterviewTime();
		LocalTime interviewEndTime = interviewStartTime.plusSeconds(totalDurationInSeconds);
		// Set the end time in the DTO
		interviewDetails.setEndTime(interviewEndTime);

		// Prepare the message

		String message = String.format("I hope this message finds you well.\r\n" + "\r\n"
				+ "Thank you for applying for the [Position Title] role at techpixe. We are impressed with your background and would like to invite you to an interview to discuss your qualifications and the opportunity further.\r\n"
				+ "\r\n"
				+ "Could you please provide your availability for a [phone/virtual/in-person] interview over the next few days? We are flexible and happy to accommodate a time that works best for you.\r\n"
				+ "\r\n"
				+ "Please let us know your preferred times, and if you have any specific questions or need additional information prior to the interview, feel free to reach out.\r\n"
				+ "\r\n" + "you have an interview scheduled on %s at %s.\r\n" + "\r\n"
				+ "We look forward to speaking with you soon.", candidate.getName(),
				interviewDetails.getInterviewDate(), interviewDetails.getInterviewTime(),
				interviewDetails.getEndTime());
//	        String message = String.format("Dear %s, you have an interview scheduled on %s at %s. The questions will be timed and will end at %s.",
//	                candidate.getName(), interviewDetails.getInterviewDate(), interviewDetails.getInterviewTime(), interviewDetails.getEndTime());

		// Send email
		emailService.sendEmail(candidate.getEmailID(), "Interview Invitation at Techpixe", message);

		Interview interview = interviewRepository.save(interviewDetails);

		// PersonalInformation personalInformation=new PersonalInformation();

		candidate.setInterview(interview);
		personalInformationRepository.save(candidate);

	}

	@Override
	public void updateInterviewByCandidateId(Long candidate_Id, LocalDate interviewDate, LocalTime interviewTime) {
		PersonalInformation candidate = personalInformationRepository.findById(candidate_Id)
				.orElseThrow(() -> new RuntimeException("Candidate not found"));

		Interview existingInterview = candidate.getInterview();
		if (existingInterview == null) {
			throw new RuntimeException("Interview not found for the candidate");
		}

		existingInterview.setInterviewDate(interviewDate);
		existingInterview.setInterviewTime(interviewTime);
		int totalDurationInSeconds = 0;
		List<Question> questions = candidate.getQuestions(); // Assuming you have a method to get questions
		for (Question question : questions) {
			totalDurationInSeconds += question.getMinimumTime();
		}
		LocalTime interviewStartTime = existingInterview.getInterviewTime();
		LocalTime interviewEndTime = interviewStartTime.plusSeconds(totalDurationInSeconds);
		// Set the end time in the DTO
		existingInterview.setEndTime(interviewEndTime);
		existingInterview.setEndTime(interviewEndTime);

		interviewRepository.save(existingInterview);
	}

	@Override
	public void saveAnswers(Long candidateId, ExamResultDto answers) {
	    PersonalInformation candidate = personalInformationRepository.findById(candidateId)
	            .orElseThrow(() -> new RuntimeException("Candidate not found"));

	    String overallPercentageStr = answers.getOverallPercentage().replace("%", "").trim();
	    int overallPercentage = Integer.parseInt(overallPercentageStr);

	    
	    candidate.setResult(overallPercentage);

	    for (int i = 0; i < candidate.getQuestions().size(); i++) {
	        Question oldQuestion = questionRepository.findById(candidate.getQuestions().get(i).getQuestion_Id())
	                .orElseThrow(() -> new RuntimeException("Question not found"));
	        oldQuestion.setAnswer(answers.getAnswers().get(i)); 
	        oldQuestion.setScore(overallPercentage);
	        questionRepository.save(oldQuestion);
	    }
	    
		String message = String.format("", candidate.getName());
		
		
		System.err.println(answers);
		
		String discription = "your result of the exam is " + answers.getOverallPercentage() + answers.getDecision();
							 
		
			
		emailService.sendEmail(candidate.getEmailID(), "Interview Invitation at Techpixe", message + " "+ discription);

	}
}
