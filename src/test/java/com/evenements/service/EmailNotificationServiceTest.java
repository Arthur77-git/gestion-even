package com.evenements.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EmailNotificationService class.
 */
public class EmailNotificationServiceTest {

    @Test
    void testEnvoyerNotification() {
        EmailNotificationService service = new EmailNotificationService();
        service.envoyerNotification("Test message");
        // Since this prints to console, we can't assert the output directly without capturing System.out
        // Consider redirecting System.out in a real test or using a logging framework
    }

    @Test
    void testEnvoyerNotificationAsync() throws Exception {
        EmailNotificationService service = new EmailNotificationService();
        CompletableFuture<Void> future = service.envoyerNotificationAsync("Async message");
        future.join(); // Wait for completion
        assertTrue(future.isDone());
    }
}