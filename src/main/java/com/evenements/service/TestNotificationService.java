package com.evenements.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Test implementation of NotificationService for capturing notifications.
 */
public class TestNotificationService implements NotificationService {
    private List<String> notifications;
    private CompletableFuture<Void> lastFuture;

    public TestNotificationService() {
        notifications = new ArrayList<>();
        lastFuture = null;
    }

    @Override
    public void envoyerNotification(String message) {
        notifications.add(message);
        lastFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Test Notification: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public CompletableFuture<Void> getLastFuture() {
        return lastFuture;
    }
}