package com.techpixe.ehr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.ehr.entity.Notification;
import com.techpixe.ehr.entity.EmployeeTable;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find notifications by recipient, ordered by timestamp in descending order
    List<Notification> findByRecipientOrderByTimestampDesc(EmployeeTable recipient);

	List<Notification> findAllByRecipient(EmployeeTable employee);
    
  
}
