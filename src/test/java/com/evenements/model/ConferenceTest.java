package com.evenements.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ConferenceTest {

    private Conference conference;
    private Participant participant;

    @BeforeEach
    void setUp() {
        conference = new Conference("C001", "Tech Conference", LocalDateTime.now(), "Room A", 2, "AI");
        participant = new Participant("P001", "John Doe", "john@example.com");
    }

    @Test
    void testAjouterParticipant_Success() {
        assertTrue(conference.ajouterParticipant(participant));
        assertEquals(1, conference.getParticipants().size());
    }

    @Test
    void testAjouterParticipant_CapacityReached() {
        conference.ajouterParticipant(participant);
        conference.ajouterParticipant(new Participant("P002", "Jane Doe", "jane@example.com"));
        assertThrows(IllegalStateException.class, () -> {
            conference.ajouterParticipant(new Participant("P003", "Bob Doe", "bob@example.com"));
        });
    }

    @Test
    void testAjouterParticipant_DejaInscrit() {
        conference.ajouterParticipant(participant);
        assertFalse(conference.ajouterParticipant(participant));
    }

    @Test
    void testGetTheme() {
        assertEquals("AI", conference.getTheme());
    }

    @Test
    void testAnnuler() {
        conference.annuler();
        assertTrue(conference.isAnnule());
    }
}