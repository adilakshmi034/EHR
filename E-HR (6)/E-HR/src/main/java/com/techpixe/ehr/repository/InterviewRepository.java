package com.techpixe.ehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

}
