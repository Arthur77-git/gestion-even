package com.evenements.factory;

import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementFactoryTest {

    @BeforeEach
    void setUp() {
        // No singleton reset needed for factory
    }

    @Test
    void testCreerConference() {
        Evenement event = EvenementFactory.creerEvenement(
                "conference", "C001", "Tech Conference", LocalDateTime.now(), "Room A", 100, "AI"
        );
        assertNotNull(event);
        assertTrue(event instanceof Conference);
        assertEquals("C001", event.getId());
        assertEquals("Tech Conference", event.getNom());
    }

    @Test
    void testCreerParticipant() {
        NotificationService mockService = new NotificationService() {
            @Override
            public void envoyerNotification(String message) {

            }

            @Override
            public CompletableFuture<Void> envoyerNotificationAsync(String message) {
                return CompletableFuture.completedFuture(null);
            }
        }; // Mock implementation
        Participant participant = EvenementFactory.creerParticipant(
                "P001", "John Doe", "john@example.com", mockService
        );
        assertNotNull(participant);
        assertEquals("P001", participant.getId());
        assertEquals("John Doe", participant.getNom());
        assertEquals("john@example.com", participant.getEmail());
    }
}