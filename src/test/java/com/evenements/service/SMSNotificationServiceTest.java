package com.evenements.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SMSNotificationService class.
 */
public class SMSNotificationServiceTest {

    @Test
    void testEnvoyerNotification() {
        SMSNotificationService service = new SMSNotificationService();
        service.envoyerNotification("Test message");
        // Similar to EmailNotificationService, console output can't be directly tested
    }

    @Test
    void testEnvoyerNotificationAsync() throws Exception {
        SMSNotificationService service = new SMSNotificationService();
        CompletableFuture<Void> future = service.envoyerNotificationAsync("Async message");
        future.join();
        assertTrue(future.isDone());
    }
}