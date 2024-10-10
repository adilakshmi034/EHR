package com.techpixe.ehr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Getter
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long question_Id;

	private String question;
	private int minimumTime;

	private String answer;

	private int maximumMarks;

	private int score;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "candidate_Id")
	private PersonalInformation personalInformation;

}
