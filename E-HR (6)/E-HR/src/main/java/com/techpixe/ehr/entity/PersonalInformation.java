package com.techpixe.ehr.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long candidateId;
	
	private String Name;
	private String MobileNumber;
	private String EmailID;
	private int result;
	private LocalDate interviewDate;
	private LocalTime interviewTime;
	private String jobRole;
	@Lob
	@Column(columnDefinition = "TEXT")
	private String resumeTextData;
	@Lob
	private byte[] resume; // Store the resume file as a byte array

	// @JsonIgnoreProperties("personalInformation")
	@OneToMany(mappedBy = "personalInformation", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	@JsonManagedReference
	private List<Question> Questions = new ArrayList<>();

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "interview_Id")
	private Interview interview;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_Id")
	@JsonBackReference
	private User user;

}
