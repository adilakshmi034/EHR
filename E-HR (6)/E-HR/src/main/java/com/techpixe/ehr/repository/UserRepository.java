package com.techpixe.ehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByMobileNumber(Long mobileNumber);

	User findByEmail(String email);

}
