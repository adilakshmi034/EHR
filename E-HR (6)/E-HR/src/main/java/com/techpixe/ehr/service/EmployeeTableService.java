package com.techpixe.ehr.service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.User;

public interface EmployeeTableService {
	EmployeeTable createEmployee(String fullName, Date dob, String gender,
			String maritalStatus, String nationality, String address, String city, String state, String country,
			String emailId, Long contactNo, String identification, String idNumber, String employeeType,
			LocalDate joiningDate, String bloodGroup, MultipartFile photograph, Long userId)throws IOException;

	public List<EmployeeTable> getAllEmployees();

	public Optional<EmployeeTable> getEmployeeById(Long id);

	 public Optional<EmployeeTable> updateEmployee(
	            Long id, String fullName, String empCode, Date dob, String gender,
	            String maritalStatus, String nationality, String address, String city,
	            String state, String country, String emailId, Long contactNo, String identification,
	            String idNumber, String employeeType, LocalDate joiningDate, String bloodGroup,
	            String password, String role, String designation, String department, Long panNo,
	            String bankName, Long bankAccountNo, String iFSCCode, String pfAccountNo,
	            Integer totalDays, Integer presentDays, byte[] photograph, User user) ;
	public void deleteEmployee(Long id);

}
