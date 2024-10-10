package com.techpixe.ehr.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.ehr.dto.ErrorResponseDto;
import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.User;
import com.techpixe.ehr.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> addRegistration(@RequestParam Double subscription_id, @RequestParam String email,
			@RequestParam Long mobileNumber, @RequestParam String fullName,
			@RequestParam(required = false) String planType,@RequestParam String companyName,@RequestParam String authorizedCompanyName,@RequestParam MultipartFile logo,@RequestParam String address) throws IOException {

		System.err.println(" " + subscription_id + " " + email + " " + mobileNumber + " " + fullName + " " + planType);

		User registration = userService.registration(email, subscription_id, mobileNumber, fullName, planType,companyName,authorizedCompanyName,logo,address);

		System.out.println("user registration success");

		return ResponseEntity.ok(registration);

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, @RequestParam String password) {
		if (emailOrMobileNumber != null) {
			if (isEmail(emailOrMobileNumber)) {
				return userService.loginByEmail(emailOrMobileNumber, password);
			} else if (isMobileNumber(emailOrMobileNumber)) {
				return userService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
			} else {
				ErrorResponseDto errorResponse = new ErrorResponseDto();
				errorResponse
						.setError("Invalid emailOrMobileNumber format. Please provide a valid email or mobile number.");
				return ResponseEntity.internalServerError().body(errorResponse);
			}
		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid input. Email or mobile number must be provided.");
			return ResponseEntity.internalServerError().body(errorResponse);
		}

	}

	private boolean isEmail(String emailOrMobileNumber) {
		return emailOrMobileNumber.contains("@");
	}

	private boolean isMobileNumber(String emailOrMobileNumber) {
		return emailOrMobileNumber.matches("\\d+");
	}

	@PostMapping("/changepassword/{user_Id}")
	public ResponseEntity<?> changePassword(@PathVariable Long user_Id, @RequestParam String password,
			@RequestParam String confirmPassword) {
		if (password != null && confirmPassword != null) {
			return userService.changePassword(user_Id, password, confirmPassword);
		} else {
			ErrorResponseDto error = new ErrorResponseDto();
			error.setError("*********Password is not present*************");
			return ResponseEntity.internalServerError().body(error);
		}
	}

	// *************FORGOT PASSWORD****************
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestParam String email) {
		if (email != null) {
			if (isEmail(email)) {
				return userService.forgotPassword(email);
			} else {
				ErrorResponseDto error = new ErrorResponseDto();
				error.setError("*********Invalid Email Pattern *************");
				return ResponseEntity.internalServerError().body(error);
			}
		} else {
			ErrorResponseDto error = new ErrorResponseDto();
			error.setError("*********Email is not present*************");
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getJobDetailsById(@PathVariable("id") Long userId) {
		Optional<User> jobDetails = userService.getByUserId(userId);
		return jobDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> allUsers() {
		List<User> allUsers = userService.allUser();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);

	}
	
	@GetMapping("/{userId}/employees")
    public List<EmployeeTable> getEmployeesByUserId(@PathVariable Long userId) {
        return userService.getEmployeesByUserId(userId);
    }
}
