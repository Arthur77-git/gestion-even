package com.evenements.service;

import java.util.concurrent.CompletableFuture;

public class EmailNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("[EMAIL] " + message);
    }

    public CompletableFuture<Void> envoyerNotificationAsync(String message) {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Simulation délai d'envoi
                envoyerNotification(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}