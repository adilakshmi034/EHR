package com.techpixe.ehr.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.ehr.dto.ErrorResponseDto;
import com.techpixe.ehr.dto.UserDto;
import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.SubscriptionPlan;
import com.techpixe.ehr.entity.User;
import com.techpixe.ehr.repository.SubscriptionPlanRepository;
import com.techpixe.ehr.repository.UserRepository;
import com.techpixe.ehr.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	SubscriptionPlanRepository subscriptionPlanRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("$(spring.mail.username)")
	private String fromMail;

	@Override
	public User registration(String email, Double subscriptionPlan, Long mobileNumber, String fullName, String planType,
			String companyName, String authorizedCompanyName, MultipartFile logo, String address) throws IOException {

		Optional<SubscriptionPlan> sub = subscriptionPlanRepository.findById(subscriptionPlan.longValue());

		User user = new User();
		user.setFullName(fullName);
		user.setEmail(email);
		user.setMobileNumber(mobileNumber);
		user.setSubscriptionPlan(sub.get());
		String password = generatePassword();
		user.setPassword(password);
		user.setAuthorizedCompanyName(authorizedCompanyName);
		user.setCompanyName(companyName);
		user.setAddress(address);
		user.setLogo(logo.getBytes());
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromMail);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("Registration completed Successfully in User\n");
		simpleMailMessage.setText("Dear " + fullName
				+ ",\n\n Thank you for singing Up for E_HR! click below to get  started on your web or mobile device .\n\nPlease check your registered email and generted passowrd\n UserEmail  :"
				+ email + "\n Registered MobileNumber :" + mobileNumber + "\n Temporary Password   :" + password
				+ "\n\n"
				+ "you will be required to reset the temporary password upon login\n\n\n if you have any question or if you would like to request a call-back,please email us at support info@techpixe.com");
		javaMailSender.send(simpleMailMessage);

		return userRepository.save(user);

	}

	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String DIGITS = "0123456789";

	public static String generatePassword() {
		Random random = new Random();

		StringBuilder lettersBuilder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(LETTERS.length());
			lettersBuilder.append(LETTERS.charAt(index));
		}

		StringBuilder digitsBuilder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(DIGITS.length());
			digitsBuilder.append(DIGITS.charAt(index));
		}

		return lettersBuilder.toString() + digitsBuilder.toString();
	}

	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password) {
		User user = userRepository.findByMobileNumber(mobileNumber);

		if (user != null && user.getPassword().equals(password)) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getUser_Id());
			userDto.setFullName(user.getFullName());
			userDto.setEmail(user.getEmail());
			userDto.setMobileNumber(user.getMobileNumber());
			userDto.setPassword(user.getPassword());
			return ResponseEntity.ok(userDto);
		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid mobile number or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) {
		User user = userRepository.findByEmail(email);
		System.out.println(email);

		if (user != null && user.getPassword().equals(password)) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getUser_Id());
			userDto.setFullName(user.getFullName());
			userDto.setEmail(user.getEmail());
			userDto.setMobileNumber(user.getMobileNumber());
			userDto.setPassword(user.getPassword());
			return ResponseEntity.ok(userDto);

		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	// ***************CHANGE PASSWORD*************************

	@Override
	public ResponseEntity<?> changePassword(Long user_Id, String password, String confirmPassword) {
		User user = userRepository.findById(user_Id)
				.orElseThrow(() -> new RuntimeException("user is not present: " + user_Id));

		if (user != null && user.getPassword().equals(password)) {

			System.out.println("user Password is Successfully Changed");

			UserDto userDto = new UserDto();
			userDto.setId(user_Id);
			userDto.setFullName(user.getFullName());
			userDto.setEmail(user.getEmail());
			userDto.setMobileNumber(user.getMobileNumber());
			userDto.setPassword(confirmPassword);

			user.setPassword(confirmPassword);
			userRepository.save(user);
			return ResponseEntity.ok(userDto);

		} else {

			System.out.println("Password and Confirm Password are not Matching");

			ErrorResponseDto error = new ErrorResponseDto();
			error.setError("######################Password is not present#################");
			// return ResponseEntity.internalServerError().body(error);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
	}

	@Override
	public ResponseEntity<?> forgotPassword(String email) {
		User user = userRepository.findByEmail(email);

		if (user != null) {

			System.out.println("User : Password will be sent to E-Mail Number");

			UserDto userDTO = new UserDto();
			userDTO.setId(user.getUser_Id());
			userDTO.setFullName(user.getFullName());
			userDTO.setEmail(email);
			userDTO.setMobileNumber(user.getMobileNumber());
			userDTO.setPassword(user.getPassword());

			String password = generatePassword();
			userDTO.setPassword(password);

			user.setPassword(password);
			userRepository.save(user);

			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(fromMail);
			simpleMailMessage.setTo(email);
			simpleMailMessage.setSubject("password changed Successfully in E_HR application\n");
			simpleMailMessage.setText("Dear " + user.getFullName()
					+ "\n\nPlease check your  email and generated password\n UserEmail  :" + email
					+ "\n  MobileNumber :" + user.getMobileNumber() + "\n New Password   :" + password + "\n\n"
					+ "you will be required to reset the New password upon login\n\n\n if you have any question or if you would like to request a call-back,please email us at support info@techpixe.com");
			javaMailSender.send(simpleMailMessage);

			return ResponseEntity.ok(userDTO);
		} else {

			System.out.println("****Invalid Email****");

			ErrorResponseDto errorResponseDto = new ErrorResponseDto();
			errorResponseDto.setError("Email is not matching");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
		}
	}

	@Override
	public Optional<User> getByUserId(Long user_Id) {
		return userRepository.findById(user_Id);
	}

	@Override
	public List<User> allUser() {
		List<User> fetchAllUsers = userRepository.findAll();
		if (fetchAllUsers == null) {
			throw new ResponseStatusException(HttpStatus.OK, "No Users Found");
		}
		return fetchAllUsers;
	}

	@Override
	public List<EmployeeTable> getEmployeesByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		return user.getEmployeeTables();
	}
}
