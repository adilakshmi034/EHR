package com.techpixe.ehr.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.Attedence;

public interface AttedenceRepository extends JpaRepository<Attedence, Long>{
	Attedence findByEmployeeTableIdAndDate(Long empId, LocalDate date);

}
