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
public class Holiday {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long holidayId;
	private String holidayTitle;
	private String description;
	private String holidayDate;
	private String holidayType;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_Id")
	@JsonBackReference
	private User user;

}
