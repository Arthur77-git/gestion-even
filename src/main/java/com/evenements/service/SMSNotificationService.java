package com.evenements.service;

/**
 * Service for sending notifications via SMS.
 */
public class SMSNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("[SMS] " + message);
    }
}