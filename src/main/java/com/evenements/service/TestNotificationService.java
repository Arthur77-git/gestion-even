package com.evenements.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TestNotificationService implements NotificationService {
    private List<String> notifications;
    private CompletableFuture<Void> lastFuture;

    public TestNotificationService() {
        notifications = new ArrayList<>();
        lastFuture = null;
    }

    @Override
    public CompletableFuture<Void> envoyerNotification(String message) {
        notifications.add(message);
        lastFuture = CompletableFuture.runAsync(() -> {
            try {
                // Simuler un délai pour une notification asynchrone
                Thread.sleep(1000);
                System.out.println("Test Notification: " + message);
            } catch (InterruptedException e) {
                System.err.println("Erreur lors de l'envoi de la notification: " + e.getMessage());
            }
        });
        return lastFuture;
    }

    public List<String> getNotifications() {
        return new ArrayList<>(notifications); // Retourne une copie pour encapsulation
    }

    public CompletableFuture<Void> getLastFuture() {
        return lastFuture;
    }
}