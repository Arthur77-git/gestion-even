package com.evenements.model;

import com.evenements.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Participant class.
 */
public class ParticipantTest {

    @Mock
    private NotificationService notificationService;

    private Participant participant;
    private final String id = "1";
    private final String nom = "John Doe";
    private final String email = "john@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participant = new Participant(id, nom, email, notificationService);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(id, participant.getId());
        assertEquals(nom, participant.getNom());
        assertEquals(email, participant.getEmail());
    }

    @Test
    void testDefaultConstructor() {
        Participant emptyParticipant = new Participant();
        assertNull(emptyParticipant.getId());
        assertNull(emptyParticipant.getNom());
        assertNull(emptyParticipant.getEmail());
    }

    @Test
    void testSetters() {
        String newId = "2";
        String newNom = "Jane Doe";
        String newEmail = "jane@example.com";
        NotificationService newNotificationService = mock(NotificationService.class);

        participant.setId(newId);
        participant.setNom(newNom);
        participant.setEmail(newEmail);
        participant.setNotificationService(newNotificationService);

        assertEquals(newId, participant.getId());
        assertEquals(newNom, participant.getNom());
        assertEquals(newEmail, participant.getEmail());
        assertEquals(newNotificationService, participant.getNotificationService());
    }

    @Test
    void testUpdateWithNotificationService() {
        String message = "Test message";
        participant.update(message);
        verify(notificationService, times(1))
                .envoyerNotification("Notification pour " + nom + " (" + email + "): " + message);
    }

    @Test
    void testUpdateWithoutNotificationService() {
        participant = new Participant(id, nom, email, null);
        participant.update("Test message");
        verifyNoInteractions(notificationService);
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(participant.equals(participant));
    }

    @Test
    void testEqualsSameId() {
        Participant other = new Participant(id, "Different Name", "different@example.com", null);
        assertTrue(participant.equals(other));
    }

    @Test
    void testEqualsDifferentId() {
        Participant other = new Participant("2", nom, email, notificationService);
        assertFalse(participant.equals(other));
    }

    @Test
    void testEqualsNull() {
        assertFalse(participant.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(participant.equals(new Object()));
    }

    @Test
    void testHashCodeConsistency() {
        Participant other = new Participant(id, "Different Name", "different@example.com", null);
        assertEquals(participant.hashCode(), other.hashCode());
    }

    @Test
    void testHashCodeDifferentId() {
        Participant other = new Participant("2", nom, email, notificationService);
        assertNotEquals(participant.hashCode(), other.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Participant{id='1', nom='John Doe', email='john@example.com'}";
        assertEquals(expected, participant.toString());
    }

    @Test
    void testConstructorWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(null, nom, email, notificationService));
        assertThrows(IllegalArgumentException.class, () -> new Participant("", nom, email, notificationService));
    }

    @Test
    void testConstructorWithInvalidNom() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, null, email, notificationService));
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, "", email, notificationService));
    }

    @Test
    void testConstructorWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, nom, "invalid-email", notificationService));
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, nom, null, notificationService));
    }
}