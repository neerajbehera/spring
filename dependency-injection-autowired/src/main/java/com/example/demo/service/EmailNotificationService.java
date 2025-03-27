package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("emailNotificationService")
public class EmailNotificationService implements NotificationService {

    @Override
    public void sendConfirmation(String email, String message) {
        // Simulate email sending
        System.out.println("[CONSOLE] Email notification: " + message);
    }
}