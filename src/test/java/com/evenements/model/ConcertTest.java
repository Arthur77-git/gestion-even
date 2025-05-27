package com.evenements.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ConcertTest {

    private Concert concert;
    private Participant participant;

    @BeforeEach
    void setUp() {
        concert = new Concert("CONC1", "Rock Fest", LocalDateTime.now(), "Stade", 50000, "Metallica", "Rock");
        participant = new Participant("P001", "Alice", "alice@email.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("CONC1", concert.getId());
        assertEquals("Rock Fest", concert.getNom());
        assertEquals("Stade", concert.getLieu());
        assertEquals(50000, concert.getCapaciteMax());
        assertEquals("Metallica", concert.getArtiste());
        assertEquals("Rock", concert.getGenre());
    }

    @Test
    void testAjouterParticipant() {
        assertTrue(concert.ajouterParticipant(participant));
        assertEquals(1, concert.getParticipants().size());
    }

    @Test
    void testAjouterParticipantDejaInscrit() {
        concert.ajouterParticipant(participant);
        assertFalse(concert.ajouterParticipant(participant));
    }

    @Test
    void testAjouterParticipantCapaciteMaxAtteinte() {
        // Set a small capacity for testing
        concert = new Concert("CONC1", "Rock Fest", LocalDateTime.now(), "Stade", 1, "Metallica", "Rock");
        concert.ajouterParticipant(participant);
        Participant anotherParticipant = new Participant("P002", "Bob", "bob@email.com");
        assertThrows(IllegalStateException.class, () -> concert.ajouterParticipant(anotherParticipant));
    }

    @Test
    void testAnnuler() {
        concert.ajouterParticipant(participant);
        concert.annuler();
        assertTrue(concert.isAnnule());
    }

    @Test
    void testModifierEvenement() {
        concert.ajouterParticipant(participant);
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        concert.modifierEvenement("New Fest", newDate, "New Stade");
        assertEquals("New Fest", concert.getNom());
        assertEquals(newDate, concert.getDate());
        assertEquals("New Stade", concert.getLieu());
    }
}