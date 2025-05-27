package com.evenements.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Test implementation of NotificationService for capturing notifications.
 * This class is thread-safe and simulates asynchronous notification sending.
 */
public class TestNotificationService implements NotificationService {
    private final List<String> notifications;
    private CompletableFuture<Void> lastFuture;

    public TestNotificationService() {
        this.notifications = Collections.synchronizedList(new ArrayList<>());
        this.lastFuture = null;
    }

    /**
     * Sends a notification message asynchronously and stores it for testing.
     *
     * @param message the message to send
     * @throws IllegalArgumentException if the message is null or empty
     */
    @Override
    public void envoyerNotification(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be null or empty");
        }
        notifications.add(message);
        lastFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Simulate delay
                System.out.println("[Test Notification] Processed: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Test Notification] Interrupted: " + e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Void> envoyerNotificationAsync(String asyncMessage) {
        return null;
    }

    /**
     * Returns a copy of the list of sent notifications.
     *
     * @return a list of notification messages
     */
    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }

    /**
     * Returns the CompletableFuture of the last notification sent.
     *
     * @return the last CompletableFuture, or a completed future if none exists
     */
    public CompletableFuture<Void> getLastFuture() {
        return lastFuture != null ? lastFuture : CompletableFuture.completedFuture(null);
    }

    /**
     * Clears all stored notifications and resets the last future.
     * Useful for resetting the state between tests.
     */
    public void clearNotifications() {
        notifications.clear();
        lastFuture = null;
    }
}