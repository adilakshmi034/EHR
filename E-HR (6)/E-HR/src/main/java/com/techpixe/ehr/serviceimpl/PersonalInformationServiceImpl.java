package com.techpixe.ehr.serviceimpl;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techpixe.ehr.dto.ExamResultDto;
import com.techpixe.ehr.dto.PersonalInformationDto;
import com.techpixe.ehr.entity.AddJobDetails;
import com.techpixe.ehr.entity.PersonalInformation;
import com.techpixe.ehr.entity.Question;
import com.techpixe.ehr.entity.User;
import com.techpixe.ehr.repository.PersonalInformationRepository;
import com.techpixe.ehr.repository.QuestionRepository;
import com.techpixe.ehr.repository.UserRepository;
import com.techpixe.ehr.service.PersonalInformationService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {
	@Autowired
	private PersonalInformationRepository personalInformationRepository;

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	JavaMailSender javaMailSender;

	
	@Override
    public PersonalInformation getPersonalInformationById(String emailid) {
        return personalInformationRepository.findByEmailID(emailid);
    }
	
	
	@Override
	public PersonalInformation savePersonalInformation(MultipartFile file, Long user_Id) throws Exception {
		PersonalInformation personalInformation = new PersonalInformation();
		byte[] fileBytes = file.getBytes();
		personalInformation.setResume(fileBytes);
		User user = userRepository.findById(user_Id).orElseThrow(() -> new RuntimeException(user_Id + " is not found"));
		String extractedText = "";
		String allJobTitles = user.getAddJobDetails().stream().map(AddJobDetails::getJobTitle)
				.collect(Collectors.joining(", "));
		personalInformation.setUser(user);
		personalInformation.setJobRole(allJobTitles);
//		personalInformation.setJobRole(jobDetails.getJobTitle());

		if (file.getOriginalFilename().endsWith(".pdf")) {
			try (PDDocument document = PDDocument.load(fileBytes)) {
				PDFTextStripper pdfStripper = new PDFTextStripper();
				extractedText = pdfStripper.getText(document);
				System.err.println(extractedText + "extractedText");

				personalInformation.setResumeTextData(extractedText);

			}
		} else if (file != null && file.getOriginalFilename().endsWith(".docx")) {
			try (XWPFDocument docx = new XWPFDocument(new ByteArrayInputStream(fileBytes))) {
				XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
				extractedText = extractor.getText();
				personalInformation.setResumeTextData(extractedText);

			}
		} else if (file != null && file.getOriginalFilename().endsWith(".doc")) {
			try (HWPFDocument doc = new HWPFDocument(new ByteArrayInputStream(fileBytes))) {
				WordExtractor extractor = new WordExtractor(doc);
				extractedText = extractor.getText();
				personalInformation.setResumeTextData(extractedText);

			}
		} else {
			// Fallback to Tika for other file types
			Tika tika = new Tika();
			extractedText = tika.parseToString(new ByteArrayInputStream(fileBytes));
			System.err.println(extractedText + "extractedText");

			personalInformation.setResumeTextData(extractedText);

		}

		return personalInformationRepository.save(personalInformation);
	}

	@Override
	public ResponseEntity<String> savePersonalDateInformation(LocalDate date, LocalTime fromTime, String jobRole,
			Long user_Id) throws Exception {
		// PersonalInformation personalInformation = new PersonalInformation();

		User user = userRepository.findById(user_Id).orElseThrow(() -> new RuntimeException(user_Id + " is not found"));
		PersonalInformation personalInformation = personalInformationRepository
				.findTopByUserOrderByCandidateIdDesc(user);

		System.err.println(personalInformation.getCandidateId() + "candidateId");
		personalInformation.setJobRole(jobRole);
		personalInformation.setInterviewDate(date);
		personalInformation.setInterviewTime(fromTime);
		personalInformation.getResumeTextData();
		PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformation);
		Map<String, Object> jsonData = new HashMap<>();
		jsonData.put("resumeTextData", personalInformation.getResumeTextData());
		jsonData.put("jobRole", jobRole);
		// System.err.println(jsonData+"jsonData");
		// Send the JSON data to the webhook URL
		ResponseEntity<String> webhookResponse = sendPostRequestToWebhook(jsonData);

		// Convert the webhook response JSON to DTO
		ObjectMapper objectMapper = new ObjectMapper();
		PersonalInformationDto webhookResponseDTO = objectMapper.readValue(webhookResponse.getBody(),
				PersonalInformationDto.class);

		savedPersonalInformation.setName(webhookResponseDTO.getPersonalInformation().getName());
		savedPersonalInformation.setMobileNumber(webhookResponseDTO.getPersonalInformation().getMobileNumber());
		savedPersonalInformation.setEmailID(webhookResponseDTO.getPersonalInformation().getEmailID());

		// Save the updated personal information with webhook data
		savedPersonalInformation = personalInformationRepository.save(savedPersonalInformation);
		saveQuestions(savedPersonalInformation, webhookResponseDTO.getQuestions(),
				personalInformation.getCandidateId());
		sendInterviewDetailsEmail(savedPersonalInformation);

		return new ResponseEntity<>(webhookResponse.getBody(), webhookResponse.getStatusCode());

	}

	private void sendInterviewDetailsEmail(PersonalInformation personalInformation) {
		String to = personalInformation.getEmailID();
		String subject = "Interview Details for the Job Role: " + personalInformation.getJobRole();
		String body = "Dear Candidate,\n\n" + "Thank you for applying for the position of "
				+ personalInformation.getJobRole() + ".\n" + "Your interview is scheduled on "
				+ personalInformation.getInterviewDate() + " at " + personalInformation.getInterviewTime() + ".\n\n"
				+ "Best regards,\nHR Team";

		sendEmail(to, subject, body);
	}

	private void sendEmail(String to, String subject, String body) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		}
	}

	public void saveQuestions(PersonalInformation personalInformation, List<Question> questions, Long CandidateId) {
		for (Question question : questions) {
//	        Question question = new Question();
			question.setQuestion(question.getQuestion());
			question.setMinimumTime(question.getMinimumTime());
			question.setMaximumMarks(question.getMaximumMarks());
			question.setPersonalInformation(personalInformation);
			questionRepository.save(question);
		}
	}

	private ResponseEntity<String> sendPostRequestToWebhook(Map<String, Object> jsonData) {
		// Webhook URL
		String webhookUrl = "https://hook.eu2.make.com/p73auaerf1i2r67esein6l8nvtjuaowq";

		// Create RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create HttpEntity containing the JSON data and headers
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonData, headers);

		// Send the POST request and return the response
		return restTemplate.postForEntity(webhookUrl, request, String.class);
	}

	public PersonalInformation getPersonalInformationByCandidateId(Long candidate_Id) {
		return personalInformationRepository.findById(candidate_Id).orElseThrow(
				() -> new RuntimeException("candidate not found for saving personal information " + candidate_Id));
	}

	public PersonalInformation savePersonalInformation(PersonalInformation personalInformation) {

		return personalInformationRepository.save(personalInformation);

	}

	public void saveQuestions(Long candidate_Id, List<Question> questions, PersonalInformation personalInformation) {
		for (Question question : questions) {
			question.setPersonalInformation(personalInformation);
			questionRepository.save(question);
		}

	}

	@Override
	public List<PersonalInformation> getAllPersonalInformation() {
		List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
		// Add logging here to verify the contents of the list
		return personalInformationList;
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
	}



	
	
	
	@Override
	public boolean isVerified(String email) {
	    System.err.println("Email received: " + email);
	    
	    PersonalInformation personalInformation = personalInformationRepository.findByEmailID(email);
	    
	    if (personalInformation != null) {
	        System.err.println("PersonalInformation found with ID: " + personalInformation.getCandidateId());

	        LocalDate currentDate = LocalDate.now();
	        LocalTime currentTime = LocalTime.now();

	        System.err.println("Current Date: " + currentDate);
	        System.err.println("Current Time: " + currentTime);

	        System.err.println("Interview Date: " + personalInformation.getInterviewDate());
	        System.err.println("Interview Time: " + personalInformation.getInterviewTime());

	        if (currentDate.equals(personalInformation.getInterviewDate())) {
	            System.err.println("Date matches!");

	            if (!currentTime.isBefore(personalInformation.getInterviewTime())) {
	                System.err.println("Time is on or after the interview time.");
	                return true;
	            } else {
	                System.err.println("Current time is before the interview time.");
	            }
	        } else {
	            System.err.println("Date does not match.");
	        }
	    } else {
	        System.err.println("No PersonalInformation found for email: " + email);
	    }

	    return false; 
	}

	
	
	
	@Override
    public List<Question> getAllQuestionsByPersonalInformationId(Long personalInformationId) {
        PersonalInformation personalInformation = personalInformationRepository.findById(personalInformationId)
                .orElseThrow(() -> new RuntimeException("Personal Information not found with id: " + personalInformationId));
        return personalInformation.getQuestions();
    }
	

}

//	@Override
//	public PersonalInformation addPersonalInformation(Long id, String name, String mobileNumber, String emailID, List<Question> questions) {
//	    // Check if PersonalInformation with the given ID exists
//	    Optional<PersonalInformation> optionalPersonalInformation = personalInformationRepository.findById(id);
//
//	    if (optionalPersonalInformation.isPresent()) {
//	        PersonalInformation personalInformation = optionalPersonalInformation.get();
//	        personalInformation.setName(name);
//	        personalInformation.setMobileNumber(mobileNumber);
//	        personalInformation.setEmailID(emailID);
//
//	        // Clear the existing questions to avoid duplicates
//	        personalInformation.getQuestions().clear();
//	        
//	        personalInformation.getQuestions().stream()
//                    .map(dto -> {
//                        Question question = new Question();
//                        question.setQuestion(question.getQuestion());
//                        question.setMinimumTime(dto.getMinimumTime());
//                        return question;
//                    }).collect(Collectors.toList());
//	        
////	      
////	        for (Question question : questions) {
////
////	            question.setPersonalInformation(personalInformation);
////	            // Add the question to the PersonalInformation's list
////	            personalInformation.getQuestions().add(question);
////	        }
//
//	        // Save updated PersonalInformation
//	        personalInformation = personalInformationRepository.save(personalInformation);
//
//	        return personalInformation;
//	    } else {
//	        // Handle case where the PersonalInformation with the given ID does not exist
//	        throw new RuntimeException("Personal Information not found for ID: " + id);
//	    }
//	}
//
//
//	
//}
//	@Override
//	public PersonalInformation addPersonalInformation(Long id, String name, String mobileNumber, String emailID,
//			List<Question> questions) {
//		// Check if PersonalInformation with the given ID exists
//		Optional<PersonalInformation> optionalPersonalInformation = personalInformationRepository.findById(id);
//
//		PersonalInformation personalInformation;
//		if (optionalPersonalInformation.isPresent()) {
//			// Update existing PersonalInformation
//			personalInformation = optionalPersonalInformation.get();
//			personalInformation.setName(name);
//			personalInformation.setMobileNumber(mobileNumber);
//			personalInformation.setEmailID(emailID);
//
//			// Save updated PersonalInformation
//			personalInformation = personalInformationRepository.save(personalInformation);
//
//			// Clear existing questions if any
//			//questionRepository.deleteByPersonalInformationId(id);
////		} else {
////			// Create a new PersonalInformation
////			personalInformation = new PersonalInformation();
////			personalInformation.setName(name);
////			personalInformation.setMobileNumber(mobileNumber);
////			personalInformation.setEmailID(emailID);
////
////			// Save new PersonalInformation
////			personalInformation = personalInformationRepository.save(personalInformation);
////		}
//
//		// Associate questions with the PersonalInformation
//		for (Question question : questions) {
//			question.setPersonalInformation(personalInformation);
//			//personalInformation.setQuestions(question.getQuestion_Id());
//			questionRepository.save(question);
//		}
//
//		return personalInformation;
//	}
