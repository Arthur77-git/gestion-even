package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConcertTest {
    private Concert concert;

    @BeforeEach
    void setUp() {
        concert = new Concert("C001", "Jazz Night", LocalDateTime.now(), "Paris", 100, "Ella Jazz", "Jazz");
    }

    @Test
    void testGetArtiste() {
        assertEquals("Ella Jazz", concert.getArtiste());
    }

    @Test
    void testGetGenreMusical() {
        assertEquals("Jazz", concert.getGenreMusical());
    }

    @Test
    void testAfficherDetails() {
        concert.afficherDetails();
    }

    @Test
    void testAjouterParticipant() throws CapaciteMaxAtteinteException {
        Participant participant = new Participant("P001", "Jean Dupont", "jean@email.com");
        concert.ajouterParticipant(participant);
        assertEquals(1, concert.getParticipants().size());
        assertEquals("Jean Dupont", concert.getParticipants().get(0).getNom());
    }

    @Test
    void testAjouterParticipantCapaciteMax() {
        Concert smallConcert = new Concert("C002", "Rock Fest", LocalDateTime.now(), "Lyon", 1, "The Band", "Rock");
        Participant p1 = new Participant("P001", "Jean Dupont", "jean@email.com");
        assertDoesNotThrow(() -> smallConcert.ajouterParticipant(p1));
        Participant p2 = new Participant("P002", "Marie Martin", "marie@email.com");
        assertThrows(CapaciteMaxAtteinteException.class, () -> smallConcert.ajouterParticipant(p2));
    }

    @Test
    void testAnnuler() {
        concert.annuler();
        assertTrue(concert.isAnnule());
    }
}