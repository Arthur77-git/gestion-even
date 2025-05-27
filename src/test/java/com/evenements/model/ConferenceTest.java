package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.ParticipantDejaInscritException;
import com.evenements.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Conference class.
 * This class tests the core functionality of the Conference model,
 * including participant management, theme retrieval, and event cancellation.
 */
public class ConferenceTest {

    private Conference conference;
    private Participant participant;

    @Mock
    private NotificationService notificationService;

    /**
     * Sets up the test environment before each test method.
     * Initializes mocks and creates test instances of Conference and Participant.
     * Uses try-with-resources to handle Mockito's AutoCloseable resource.
     */
    @BeforeEach
    void setUp() {
        try (var mocks = MockitoAnnotations.openMocks(this)) {
            conference = new Conference("C001", "Tech Conference", LocalDateTime.now(), "Room A", 2, "AI");
            participant = new Participant("P001", "John Doe", "john@example.com", notificationService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize mocks", e);
        }
    }

    // Test methods grouped by functionality

    /**
     * Tests the successful addition of a participant to the conference.
     * Verifies that the participant is added and the participant count increases.
     */
    @Test
    void testAjouterParticipant_Success() throws CapaciteMaxAtteinteException, ParticipantDejaInscritException {
        assertTrue(conference.ajouterParticipant(participant));
        assertEquals(1, conference.getParticipants().size());
    }

    /**
     * Tests the behavior when the conference capacity is reached.
     * Verifies that an exception is thrown when attempting to add a participant beyond capacity.
     */
    @Test
    void testAjouterParticipant_CapacityReached() throws CapaciteMaxAtteinteException, ParticipantDejaInscritException {
        conference.ajouterParticipant(participant);
        conference.ajouterParticipant(new Participant("P002", "Jane Doe", "jane@example.com", notificationService));
        assertThrows(CapaciteMaxAtteinteException.class, () -> {
            conference.ajouterParticipant(new Participant("P003", "Bob Doe", "bob@example.com", notificationService));
        });
    }

    /**
     * Tests the retrieval of the conference theme.
     * Verifies that the theme is correctly set and retrieved.
     */
    @Test
    void testGetTheme() {
        assertEquals("AI", conference.getTheme());
    }

    /**
     * Tests the cancellation of the conference.
     * Verifies that the conference status changes to canceled after calling annuler().
     */
    @Test
    void testAnnuler() {
        conference.annuler();
        assertTrue(conference.isAnnule());
    }
}