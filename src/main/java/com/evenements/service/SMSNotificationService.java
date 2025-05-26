package com.evenements.service;

public class SMSNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("[SMS] " + message);
    }
}