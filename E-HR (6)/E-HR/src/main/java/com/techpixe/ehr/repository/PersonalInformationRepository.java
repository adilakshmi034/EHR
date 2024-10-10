package com.techpixe.ehr.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techpixe.ehr.entity.PersonalInformation;
import com.techpixe.ehr.entity.User;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
    PersonalInformation findTopByUserOrderByUserDesc(User user);
    PersonalInformation findTopByUserOrderByCandidateIdDesc(User user);
    @Query("SELECT p FROM PersonalInformation p WHERE p.EmailID = :EmailID")
    PersonalInformation findByEmailID(String EmailID);


}
