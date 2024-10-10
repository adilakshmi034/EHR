package com.techpixe.ehr.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;

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
	
	private String designation;
	private String department;
	private Long panNo;
	private String bankName;
	private Long bankAccountNo;
	private String iFSCCode;
	private String pfAccountNo;
	private int totalDays;
	private int presentDays;

	@Lob
	private byte[] photograph;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_Id")
	@JsonBackReference
	private User user;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeTable")
	private List<AddPayHeadsToEmployee> addPayHeadsToEmployee = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "employeeTable", fetch = FetchType.EAGER)
	private List<Attedence> attedence = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "employeeTable", fetch = FetchType.EAGER)
	private List<LeaveapprovalTable> leaveapprovalTable = new ArrayList<>();
}
