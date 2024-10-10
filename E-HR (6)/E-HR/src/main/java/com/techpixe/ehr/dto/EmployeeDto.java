package com.techpixe.ehr.dto;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
	private Long id;
	private String fullName;
	private String lastName;

	private String empCode;
	private Date dob;

	private String gender;
	private String maritalStatus;
	private String nationality;
	private String address;
	private String city;
	private String state;
	private String country;
	private String emailId;
	private Long contactNo;
	private String identification;
	private String idNumber;
	private String employeeType;

	private LocalDate joiningDate;

	private String bloodGroup;
	private String password;
	private String role;

	@Lob
	private byte[] photograph;
}
