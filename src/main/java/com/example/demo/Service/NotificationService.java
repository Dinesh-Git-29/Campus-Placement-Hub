package com.example.demo.Service;

import com.example.demo.Model.Notification;
import com.example.demo.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(List<String> emails, String message) {
        for (String email : emails) {
            Notification notification = new Notification(email, message);
            notificationRepository.save(notification);
        }
    }

    public List<Notification> getNotifications(String email) {
        return notificationRepository.findByEmail(email);
    }
}
