package com.techpixe.ehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

}
