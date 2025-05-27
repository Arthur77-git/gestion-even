package com.evenements.service;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for sending notifications to participants.
 */
public interface NotificationService {
    void envoyerNotification(String message);

    CompletableFuture<Void> envoyerNotificationAsync(String asyncMessage);
}