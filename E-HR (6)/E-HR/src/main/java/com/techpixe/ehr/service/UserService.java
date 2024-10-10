package com.techpixe.ehr.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.User;

public interface UserService {

	User registration(String email, Double subscriptionPlan, Long mobileNumber, String fullName, String planType,String companyName,String authorizedCompanyName,MultipartFile logo,String address)throws IOException ;

	ResponseEntity<?> loginByEmail(String emailOrMobileNumber, String password);

	ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password);

	ResponseEntity<?> changePassword(Long user_Id, String password, String confirmPassword);

	ResponseEntity<?> forgotPassword(String email);

	Optional<User> getByUserId(Long user_Id);

	List<User> allUser();

	List<EmployeeTable> getEmployeesByUserId(Long userId);

}
