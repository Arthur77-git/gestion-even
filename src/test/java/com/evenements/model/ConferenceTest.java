package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.ParticipantDejaInscritException;
import com.evenements.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Conference class.
 */
public class ConferenceTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private Participant participant1;

    @Mock
    private Participant participant2;

    private Conference conference;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conference = new Conference("CONF1", "Conf IA", LocalDateTime.now(), "Paris", 2, "IA");
        when(participant1.getNom()).thenReturn("Alice");
        when(participant1.getEmail()).thenReturn("alice@email.com");
        when(participant2.getNom()).thenReturn("Bob");
        when(participant2.getEmail()).thenReturn("bob@email.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("CONF1", conference.getId());
        assertEquals("Conf IA", conference.getNom());
        assertEquals("Paris", conference.getLieu());
        assertEquals(2, conference.getCapaciteMax());
        assertEquals("IA", conference.getTheme());
        assertTrue(conference.getIntervenants().isEmpty());
    }

    @Test
    void testAjouterIntervenant() {
        Intervenant intervenant = new Intervenant("INT1", "Dr. Marie", "Machine Learning");
        conference.ajouterIntervenant(intervenant);
        List<Intervenant> intervenants = conference.getIntervenants();
        assertEquals(1, intervenants.size());
        assertEquals(intervenant, intervenants.get(0));

        // Adding the same intervenant should not duplicate
        conference.ajouterIntervenant(intervenant);
        assertEquals(1, intervenants.size());
    }

    @Test
    void testAjouterParticipant() throws Exception {
        conference.ajouterParticipant(participant1);
        assertEquals(1, conference.getParticipants().size());
        verify(participant1, times(1)).update("Vous êtes inscrit à l'événement: Conf IA");
    }

    @Test
    void testAjouterParticipantDejaInscrit() throws Exception {
        conference.ajouterParticipant(participant1);
        assertThrows(ParticipantDejaInscritException.class, () -> conference.ajouterParticipant(participant1));
    }

    @Test
    void testAjouterParticipantCapaciteMaxAtteinte() throws Exception {
        conference.ajouterParticipant(participant1);
        conference.ajouterParticipant(participant2);
        Participant participant3 = mock(Participant.class);
        assertThrows(CapaciteMaxAtteinteException.class, () -> conference.ajouterParticipant(participant3));
    }

    @Test
    void testAnnuler() {
        conference.ajouterParticipant(participant1);
        conference.annuler();
        assertTrue(conference.isAnnule());
        verify(participant1, times(1)).update("L'événement Conf IA a été annulé");
    }

    @Test
    void testModifierEvenement() {
        conference.ajouterParticipant(participant1);
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        conference.modifierEvenement("New Name", newDate, "New Lieu");
        assertEquals("New Name", conference.getNom());
        assertEquals(newDate, conference.getDate());
        assertEquals("New Lieu", conference.getLieu());
        verify(participant1, times(1)).update("L'événement New Name a été modifié");
    }

    @Test
    void testModifierEvenementWhenAnnule() {
        conference.annuler();
        assertThrows(IllegalStateException.class, () ->
                conference.modifierEvenement("New Name", LocalDateTime.now(), "New Lieu"));
    }
}