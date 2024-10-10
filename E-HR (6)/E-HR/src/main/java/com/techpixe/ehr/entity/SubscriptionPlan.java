package com.techpixe.ehr.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class SubscriptionPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscription_Id;

	@Column(nullable = false)
	private String planType; // Quarterly, Monthly, Yearly

	@Column(nullable = false)
	private double amount;

	@Column(nullable = true)
	private LocalDate startDate;

	@Column(nullable = true)
	private LocalDate endDate;

	@Column(length = 1000)
	private String discription;
	
	@Column(length = 1000)
    private String additionalFeatures;

	
}
