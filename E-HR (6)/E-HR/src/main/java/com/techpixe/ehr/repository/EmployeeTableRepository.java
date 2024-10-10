package com.techpixe.ehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.EmployeeTable;
import com.techpixe.ehr.entity.User;

public interface EmployeeTableRepository extends JpaRepository<EmployeeTable, Long> {
	EmployeeTable findByContactNo(Long contactNo);
	EmployeeTable findByEmailId(String emailId);

}