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
 * Unit tests for the Concert class.
 */
public class ConcertTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private Participant participant;

    private Concert concert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        concert = new Concert("CONC1", "Rock Fest", LocalDateTime.now(), "Stade", 50000, "Metallica", "Rock");
        when(participant.getNom()).thenReturn("Alice");
        when(participant.getEmail()).thenReturn("alice@email.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("CONC1", concert.getId());
        assertEquals("Rock Fest", concert.getNom());
        assertEquals("Stade", concert.getLieu());
        assertEquals(50000, concert.getCapaciteMax());
        assertEquals("Metallica", concert.getArtiste());
        assertEquals("Rock", concert.getGenreMusical());
    }

    @Test
    void testAjouterParticipant() throws Exception {
        concert.ajouterParticipant(participant);
        assertEquals(1, concert.getParticipants().size());
        verify(participant, times(1)).update("Vous êtes inscrit à l'événement: Rock Fest");
    }

    @Test
    void testAjouterParticipantDejaInscrit() throws Exception {
        concert.ajouterParticipant(participant);
        assertThrows(ParticipantDejaInscritException.class, () -> concert.ajouterParticipant(participant));
    }

    @Test
    void testAnnuler() throws CapaciteMaxAtteinteException, ParticipantDejaInscritException {
        concert.ajouterParticipant(participant);
        concert.annuler();
        assertTrue(concert.isAnnule());
        verify(participant, times(1)).update("L'événement Rock Fest a été annulé");
    }

    @Test
    void testModifierEvenement() throws CapaciteMaxAtteinteException, ParticipantDejaInscritException {
        concert.ajouterParticipant(participant);
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        concert.modifierEvenement("New Fest", newDate, "New Stade");
        assertEquals("New Fest", concert.getNom());
        assertEquals(newDate, concert.getDate());
        assertEquals("New Stade", concert.getLieu());
        verify(participant, times(1)).update("L'événement New Fest a été modifié");
    }
}