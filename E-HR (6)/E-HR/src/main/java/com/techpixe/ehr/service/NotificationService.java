package com.techpixe.ehr.service;

import java.util.List;

import com.techpixe.ehr.entity.Notification;

public interface NotificationService {

     void sendNotificationToEmployee(Long senderId, Long receiverId, String messageContent, String messageType);


    
    List<Notification> getNotificationsForEmployee(Long employeeId);

    void markNotificationAsRead(Long notificationId);

	void sendNotificationToEmployees(Long senderId, List<Long> receiverIds, String messageContent,String messageType);
}
