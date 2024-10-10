package com.techpixe.ehr.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long user_Id;

	private String fullName;
	private String email;
	private Long mobileNumber;
	private String password;
	private String companyName;
	private String authorizedCompanyName;
	private String address;
	@Lob
	private byte[] logo;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subscription_Id")
	private SubscriptionPlan subscriptionPlan;
	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<AddJobDetails> addJobDetails = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<PersonalInformation> personalInformations = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<EmployeeTable> employeeTables = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<PayHeads> payHeads = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Holiday> holiday = new ArrayList<>();

}
