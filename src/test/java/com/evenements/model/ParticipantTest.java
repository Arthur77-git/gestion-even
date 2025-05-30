package com.evenements.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    private Participant participant;

    @BeforeEach
    void setUp() {
        participant = new Participant("P001", "Jean Dupont", "jean@email.com");
    }

    @Test
    void testGetId() {
        assertEquals("P001", participant.getId());
    }

    @Test
    void testGetNom() {
        assertEquals("Jean Dupont", participant.getNom());
    }

    @Test
    void testGetEmail() {
        assertEquals("jean@email.com", participant.getEmail());
    }

    @Test
    void testRegisterToEvent() {
        participant.registerToEvent("E001");
        assertTrue(participant.getEventIds().contains("E001"));
    }

    @Test
    void testUnregisterFromEvent() {
        participant.registerToEvent("E001");
        participant.unregisterFromEvent("E001");
        assertFalse(participant.getEventIds().contains("E001"));
    }
}