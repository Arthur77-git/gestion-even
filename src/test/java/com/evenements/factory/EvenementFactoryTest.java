package com.evenements.factory;

import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Organisateur;
import com.evenements.model.Participant;
import com.evenements.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EvenementFactory class.
 */
public class EvenementFactoryTest {

    @Mock
    private NotificationService notificationService;

    @Test
    void testCreerConference() {
        Conference conference = (Conference) EvenementFactory.creerEvenement(
                "conference", "CONF1", "Conf IA", LocalDateTime.now(), "Paris", 150, "IA");
        assertEquals("CONF1", conference.getId());
        assertEquals("Conf IA", conference.getNom());
        assertEquals("Paris", conference.getLieu());
        assertEquals(150, conference.getCapaciteMax());
        assertEquals("IA", conference.getTheme());
    }

    @Test
    void testCreerConcert() {
        Concert concert = (Concert) EvenementFactory.creerEvenement(
                "concert", "CONC1", "Rock Fest", LocalDateTime.now(), "Stade", 50000, "Metallica", "Rock");
        assertEquals("CONC1", concert.getId());
        assertEquals("Rock Fest", concert.getNom());
        assertEquals("Stade", concert.getLieu());
        assertEquals(50000, concert.getCapaciteMax());
        assertEquals("Metallica", concert.getArtiste());
        assertEquals("Rock", concert.getGenreMusical());
    }

    @Test
    void testCreerEvenementInvalidType() {
        assertThrows(IllegalArgumentException.class, () ->
                EvenementFactory.creerEvenement("invalid", "ID1", "Event", LocalDateTime.now(), "Lieu", 100));
    }

    @Test
    void testCreerParticipant() {
        Participant participant = EvenementFactory.creerParticipant("P1", "Alice", "alice@email.com", notificationService);
        assertEquals("P1", participant.getId());
        assertEquals("Alice", participant.getNom());
        assertEquals("alice@email.com", participant.getEmail());
        assertEquals(notificationService, participant.getNotificationService());
    }

    @Test
    void testCreerOrganisateur() {
        Organisateur organisateur = EvenementFactory.creerOrganisateur("ORG1", "Claire", "claire@events.com", notificationService);
        assertEquals("ORG1", organisateur.getId());
        assertEquals("Claire", organisateur.getNom());
        assertEquals("claire@events.com", organisateur.getEmail());
        assertTrue(organisateur.getEvenementsOrganisesIds().isEmpty());
    }
}