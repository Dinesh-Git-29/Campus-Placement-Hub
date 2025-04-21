package com.example.demo.Controller;

import com.example.demo.Model.Notification;
import com.example.demo.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{email}")
    public List<Notification> getNotifications() {
        String email="lkblkb913@gmail.com";
        return notificationService.getNotifications(email);
    }
}