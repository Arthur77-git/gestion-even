package com.evenements.service;

import java.util.concurrent.CompletableFuture;

/**
 * Service for sending notifications via SMS.
 */
public class SMSNotificationService implements NotificationService {

    /**
     * Sends a notification message via SMS.
     *
     * @param message the message to send
     * @throws IllegalArgumentException if the message is null or empty
     */
    @Override
    public void envoyerNotification(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be null or empty");
        }
        System.out.println("[SMS Notification] Sending: " + message);
    }

    @Override
    public CompletableFuture<Void> envoyerNotificationAsync(String asyncMessage) {
        return null;
    }
}