package com.techpixe.ehr.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techpixe.ehr.dto.AdminDto;
import com.techpixe.ehr.dto.EmployeeDto;
import com.techpixe.ehr.dto.ErrorResponseDto;
import com.techpixe.ehr.dto.PhotoGrapherDTo;
import com.techpixe.ehr.entity.Admin;
import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.User;
import com.techpixe.ehr.repository.AdminRepository;
import com.techpixe.ehr.repository.EmployeeTableRepository;
import com.techpixe.ehr.repository.UserRepository;
import com.techpixe.ehr.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

//	
//	@Autowired
//	private JavaMailSender javaMailSender;
//
//	@Value("$(spring.mail.username)")
//	private String fromMail;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private EmployeeTableRepository employeeTableRepository;

	@Override
	public Admin registerAdmin(String fullName, String email, Long mobileNumber, String password) {

		Admin admin = new Admin();
		admin.setFullName(fullName);
		admin.setEmail(email);
		admin.setMobileNumber(mobileNumber);
		admin.setPassword(password);
		admin.setRole("admin");
		return adminRepository.save(admin);
	}

	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password) {
		Admin user = adminRepository.findByMobileNumber(mobileNumber);
		User user1 = userRepository.findByMobileNumber(mobileNumber);
		EmployeeTable employeeDetails = employeeTableRepository.findByContactNo(mobileNumber);

		if (user != null && user.getPassword().equals(password)) {

			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			applicationFormDTo.setRole(user.getRole());

			return ResponseEntity.ok(applicationFormDTo);
		} else if (user1 != null && user1.getPassword().equals(password)) {

			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user1.getUser_Id());
			photoGrapherDTo.setFullName(user1.getFullName());
			photoGrapherDTo.setEmail(user1.getEmail());
			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
			photoGrapherDTo.setPassword(user1.getPassword());
			photoGrapherDTo.setRole("USER");
			photoGrapherDTo.setSubcriptionPlan(user1.getSubscriptionPlan().getSubscription_Id());

			return ResponseEntity.ok(photoGrapherDTo);
		} else if (employeeDetails != null && employeeDetails.getPassword().equals(password)) {
			EmployeeDto employee = new EmployeeDto();
			employee.setFullName(employeeDetails.getFullName());

			employee.setEmpCode(employeeDetails.getEmpCode());
			employee.setDob(employeeDetails.getDob());
			employee.setGender(employeeDetails.getGender());
			employee.setPassword(employeeDetails.getPassword());
			employee.setMaritalStatus(employeeDetails.getMaritalStatus());
			employee.setNationality(employeeDetails.getNationality());
			employee.setAddress(employeeDetails.getAddress());
			employee.setCity(employeeDetails.getCity());
			employee.setState(employeeDetails.getState());
			employee.setCountry(employeeDetails.getCountry());
			employee.setEmailId(employeeDetails.getEmailId());
			employee.setContactNo(employeeDetails.getContactNo());
			employee.setRole(employeeDetails.getRole());
			employee.setIdentification(employeeDetails.getIdentification());
			employee.setIdNumber(employeeDetails.getIdNumber());
			employee.setEmployeeType(employeeDetails.getEmployeeType());
			employee.setJoiningDate(employeeDetails.getJoiningDate());
			employee.setBloodGroup(employeeDetails.getBloodGroup());
			employee.setPhotograph(employeeDetails.getPhotograph());
			employee.setId(employeeDetails.getId());

			return ResponseEntity.ok(employee);

		}

		else {

			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid mobile number or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) {
		Admin user = adminRepository.findByEmail(email);
		User user1 = userRepository.findByEmail(email);
		EmployeeTable employeeDetails = employeeTableRepository.findByEmailId(email);

		if (user != null && user.getPassword().equals(password)) {
			System.out.println("");
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			applicationFormDTo.setRole(user.getRole());

			return ResponseEntity.ok(applicationFormDTo);

		} else if (user1 != null && user1.getPassword().equals(password)) {

			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user1.getUser_Id());
			photoGrapherDTo.setFullName(user1.getFullName());
			photoGrapherDTo.setEmail(user1.getEmail());
			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
			photoGrapherDTo.setPassword(user1.getPassword());
			photoGrapherDTo.setRole("USER");
			photoGrapherDTo.setSubcriptionPlan(user1.getSubscriptionPlan().getSubscription_Id());

			return ResponseEntity.ok(photoGrapherDTo);
		} else if (employeeDetails != null && employeeDetails.getPassword().equals(password)) {
			EmployeeDto employee = new EmployeeDto();
			employee.setFullName(employeeDetails.getFullName());
			employee.setId(employeeDetails.getId());
			employee.setEmpCode(employeeDetails.getEmpCode());
			employee.setDob(employeeDetails.getDob());
			employee.setGender(employeeDetails.getGender());
			employee.setPassword(employeeDetails.getPassword());
			employee.setMaritalStatus(employeeDetails.getMaritalStatus());
			employee.setNationality(employeeDetails.getNationality());
			employee.setAddress(employeeDetails.getAddress());
			employee.setCity(employeeDetails.getCity());
			employee.setState(employeeDetails.getState());
			employee.setCountry(employeeDetails.getCountry());
			employee.setEmailId(employeeDetails.getEmailId());
			employee.setContactNo(employeeDetails.getContactNo());
			employee.setRole(employeeDetails.getRole());
			employee.setIdentification(employeeDetails.getIdentification());
			employee.setIdNumber(employeeDetails.getIdNumber());
			employee.setEmployeeType(employeeDetails.getEmployeeType());
			employee.setJoiningDate(employeeDetails.getJoiningDate());
			employee.setBloodGroup(employeeDetails.getBloodGroup());
			employee.setPhotograph(employeeDetails.getPhotograph());
			return ResponseEntity.ok(employee);

		} else {

			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

//
//	// ***************CHANGE PASSWORD*************************
//
//	@Override
//	public ResponseEntity<?> changePassword(Long admin_Id, String password, String confirmPassword) {
//		Admin user = adminRepository.findById(admin_Id).orElse(null);
//		PhotoGrapher user1 = PhotoGrapherRepository.findById(admin_Id)
//				.orElseThrow(() -> new RuntimeException("photographer is not present: " + admin_Id));
//		if (user != null && user.getPassword().equals(password)) {
//
//			logger.debug("Admin Password is Successfully Changed");
//			logger.info("Request comes from Admin Controller to Admin ServiceImpl through Service");
//
//			AdminDto adminDto = new AdminDto();
//			adminDto.setAdmin_Id(user.getAdmin_Id());
//			adminDto.setFullName(user.getFullName());
//			adminDto.setEmail(user.getEmail());
//			adminDto.setMobileNumber(user.getMobileNumber());
//			adminDto.setRole(user.getRole());
//			adminDto.setPassword(confirmPassword);
//
//			user.setPassword(confirmPassword);
//			adminRepository.save(user);
//			return ResponseEntity.ok(adminDto);
//
//		} else if (user1 != null && user1.getPassword().equals(password)) {
//
//			logger.debug("PhotoGrapher Password is Successfully Changed");
//			logger.info("Request comes from Admin Controller to Admin ServiceImpl through Service");
//
//			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
//			photoGrapherDTo.setPhotographer_Id(user1.getPhotographer_Id());
//			photoGrapherDTo.setFullName(user1.getFullName());
//			photoGrapherDTo.setEmail(user1.getEmail());
//			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
//			photoGrapherDTo.setPassword(confirmPassword);
//			photoGrapherDTo.setRole(user1.getRole());
//
//			user1.setPassword(confirmPassword);
//			PhotoGrapherRepository.save(user1);
//			return ResponseEntity.ok(photoGrapherDTo);
//		} else {
//
//			logger.error("Password and Confirm Password are not Matching");
//
//			ErrorResponseDto error = new ErrorResponseDto();
//			error.setError("######################Password is not present#################");
//			// return ResponseEntity.internalServerError().body(error);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//		}
//	}
//
//	// *************FORGOT PASSWORD*****************
//
//	@Override
//	public ResponseEntity<?> forgotPassword(String email) {
//		Admin admin = adminRepository.findByEmail(email);
//		PhotoGrapher photoGrapher = PhotoGrapherRepository.findByEmail(email);
//
//		if (admin != null) {
//
//			logger.debug("Admin : Password will be sent to E-Mail Number");
//			logger.info("Request comes from Admin Controller to Admin ServiceImpl through Service");
//
//			AdminDto adminDTO = new AdminDto();
//			adminDTO.setAdmin_Id(admin.getAdmin_Id());
//			adminDTO.setFullName(admin.getFullName());
//			adminDTO.setEmail(email);
//			adminDTO.setMobileNumber(admin.getMobileNumber());
//			adminDTO.setRole(admin.getRole());
//
//			String password = generatePassword();
//			adminDTO.setPassword(password);
//
//			admin.setPassword(password);
//			adminRepository.save(admin);
//
//			return ResponseEntity.ok(adminDTO);
//		} else if (photoGrapher != null) {
//
//			logger.debug("PhotoGrapher : Password will be sent to E-Mail Number");
//			logger.info("Request comes from Admin Controller to Admin ServiceImpl through Service");
//
//			PhotoGrapherDTo photoGrapherDTO = new PhotoGrapherDTo();
//			photoGrapherDTO.setPhotographer_Id(photoGrapher.getPhotographer_Id());
//			photoGrapherDTO.setFullName(photoGrapher.getFullName());
//			photoGrapherDTO.setEmail(email);
//			photoGrapherDTO.setMobileNumber(photoGrapher.getMobileNumber());
//			photoGrapherDTO.setRole(photoGrapher.getRole());
//
//			String password = generatePassword();
//			photoGrapherDTO.setPassword(password);
//			photoGrapher.setPassword(password);
//			PhotoGrapherRepository.save(photoGrapher);
//
//			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//			simpleMailMessage.setFrom(fromMail);
//			simpleMailMessage.setTo(email);
//			simpleMailMessage.setSubject("Registration completed Successfully in GetPhoto application\n");
//			simpleMailMessage.setText("Dear " + photoGrapher.getFullName()
//					+ "\n\nPlease check your  email and generted passowrd\n UserEmail  :" + email + "\n  MobileNumber :"
//					+ photoGrapher.getMobileNumber() + "\n New Password   :" + password + "\n\n"
//					+ "you will be required to reset the New password upon login\n\n\n if you have any question or if you would like to request a call-back,please email us at support info@techpixe.com");
//			javaMailSender.send(simpleMailMessage);
//
//			return ResponseEntity.ok(photoGrapherDTO);
//		} else {
//
//			logger.error("****Invalid Email****");
//
//			ErrorResponseDto errorResponseDto = new ErrorResponseDto();
//			errorResponseDto.setError("Email is not matching");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
//		}
//	}
//
//	// **********Generate Random Password ********************
//	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//	private static final String NUMERIC_STRING = "0123456789";
//
//	public static String generatePassword() {
//		StringBuilder builder = new StringBuilder();
//		Random random = new Random();
//
//		for (int i = 0; i < 4; i++) {
//			int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
//			builder.append(ALPHA_NUMERIC_STRING.charAt(index));
//		}
//		for (int i = 0; i < 4; i++) {
//			int index = random.nextInt(NUMERIC_STRING.length());
//			builder.append(NUMERIC_STRING.charAt(index));
//		}
//
//		return builder.toString();
//	}
//
	public Optional<Admin> getAdminById(Long id) {
		return adminRepository.findById(id);
	}
}
