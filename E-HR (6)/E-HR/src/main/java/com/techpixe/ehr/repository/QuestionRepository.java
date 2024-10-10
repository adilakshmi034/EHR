package com.techpixe.ehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	// void deleteByPersonalInformationId(Long id);

}
