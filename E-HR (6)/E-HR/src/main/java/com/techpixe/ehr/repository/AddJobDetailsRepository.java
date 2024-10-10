package com.techpixe.ehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpixe.ehr.entity.AddJobDetails;
@Repository
public interface AddJobDetailsRepository extends JpaRepository<AddJobDetails,Long>{

}