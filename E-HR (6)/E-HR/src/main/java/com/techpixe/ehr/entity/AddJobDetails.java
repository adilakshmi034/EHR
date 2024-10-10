package com.techpixe.ehr.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class AddJobDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobId;

	private String jobTitle;

	private List<String> jobkeyskills;

	private LocalDate createdAt;

	private int yearsOfExperience;
	private int noOfVacancies;

	private String overallPercentage;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_Id")
	@JsonBackReference
	private User user;

	// Default constructor
	public AddJobDetails() {
	}


	public AddJobDetails(long jobId, String jobTitle, List<String> jobkeyskills, LocalDate createdAt,
			int yearsOfExperience, int noOfVacancies, String overallPercentage, User user) {
		super();
		this.jobId = jobId;
		this.jobTitle = jobTitle;
		this.jobkeyskills = jobkeyskills;
		this.createdAt = createdAt;
		this.yearsOfExperience = yearsOfExperience;
		this.noOfVacancies = noOfVacancies;
		this.overallPercentage = overallPercentage;
		this.user = user;
	}


	// Getters and Setters
	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public List<String> getJobkeyskills() {
		return jobkeyskills;
	}

	public void setJobkeyskills(List<String> jobkeyskills) {
		this.jobkeyskills = jobkeyskills;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getNoOfVacancies() {
		return noOfVacancies;
	}

	public void setNoOfVacancies(int noOfVacancies) {
		this.noOfVacancies = noOfVacancies;
	}

	public String getOverallPercentage() {
		return overallPercentage;
	}

	public void setOverallPercentage(String overallPercentage) {
		this.overallPercentage = overallPercentage;
	}
	
}
