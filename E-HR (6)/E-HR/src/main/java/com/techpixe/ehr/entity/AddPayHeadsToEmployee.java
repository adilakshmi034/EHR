package com.techpixe.ehr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddPayHeadsToEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String selectedPayHead;
	private String selectedPayHeadType;
	private Double payheadAmount;
	private String empCode;
	private String empName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empId")
	@JsonBackReference
	private EmployeeTable employeeTable;

}
