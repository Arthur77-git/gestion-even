package com.evenements.service;

/**
 * Interface for notification services.
 */
public interface NotificationService {
    void envoyerNotification(String message);

    default CompletableFuture<Void> envoyerNotificationAsync(String message) {
        return CompletableFuture.runAsync(() -> envoyerNotification(message));
    }
}