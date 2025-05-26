package com.evenements.service;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.exception.ParticipantDejaInscritException;
import com.evenements.factory.EvenementFactory;
import com.evenements.model.*;
import com.evenements.serialization.JsonSerializer;
import com.evenements.service.EmailNotificationService;
import com.evenements.service.GestionEvenements;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestionEvenementsTest {

    private GestionEvenements gestion;
    private EmailNotificationService notificationService;
    private Participant participant;
    private Conference conference;

    @BeforeEach
    void setUp() {
        gestion = GestionEvenements.getInstance();
        gestion.viderTousLesEvenements();
        notificationService = new EmailNotificationService();
        participant = new Participant("P1", "John Doe", "john@test.com", notificationService);
        conference = new Conference("C1", "Tech Conference", LocalDateTime.now().plusDays(30),
                "Paris", 100, "Intelligence Artificielle");
    }

    @Test
    void testAjouterEvenement() throws EvenementDejaExistantException {
        gestion.ajouterEvenement(conference);
        assertEquals(1, gestion.getNombreEvenements());
        assertTrue(gestion.rechercherEvenement("C1").isPresent());
    }

    @Test
    void testEvenementDejaExistant() throws EvenementDejaExistantException {
        gestion.ajouterEvenement(conference);
        assertThrows(EvenementDejaExistantException.class, () -> {
            gestion.ajouterEvenement(conference);
        });
    }

    @Test
    void testInscriptionParticipant() throws Exception {
        gestion.ajouterEvenement(conference);
        conference.ajouterParticipant(participant);
        assertEquals(1, conference.getParticipants().size());
        assertTrue(conference.getParticipants().contains(participant));
    }

    @Test
    void testCapaciteMaxAtteinte() throws Exception {
        Conference petiteConference = new Conference("C2", "Petite Conf",
                LocalDateTime.now().plusDays(15), "Lyon", 1, "Test");
        gestion.ajouterEvenement(petiteConference);

        Participant p1 = new Participant("P1", "User1", "user1@test.com", notificationService);
        Participant p2 = new Participant("P2", "User2", "user2@test.com", notificationService);

        petiteConference.ajouterParticipant(p1);

        assertThrows(CapaciteMaxAtteinteException.class, () -> {
            petiteConference.ajouterParticipant(p2);
        });
    }

    @Test
    void testParticipantDejaInscrit() throws Exception {
        gestion.ajouterEvenement(conference);
        conference.ajouterParticipant(participant);

        assertThrows(ParticipantDejaInscritException.class, () -> {
            conference.ajouterParticipant(participant);
        });
    }

    @Test
    void testAnnulationEvenement() throws Exception {
        gestion.ajouterEvenement(conference);
        conference.ajouterParticipant(participant);

        assertFalse(conference.isAnnule());
        conference.annuler();
        assertTrue(conference.isAnnule());
    }

    @Test
    void testFactory() {
        Conference conf = (Conference) EvenementFactory.creerEvenement("conference", "C1", "Test Conf",
                LocalDateTime.now(), "Test", 50, "IA");
        assertEquals("IA", conf.getTheme());

        Concert concert = (Concert) EvenementFactory.creerEvenement("concert", "CO1", "Test Concert",
                LocalDateTime.now(), "Test", 200, "Artist", "Rock");
        assertEquals("Artist", concert.getArtiste());
        assertEquals("Rock", concert.getGenreMusical());
    }

    @Test
    void testSerialisationJson() throws IOException, EvenementDejaExistantException {
        gestion.ajouterEvenement(conference);

        JsonSerializer jsonSerializer = new JsonSerializer();
        String filepath = "test_events.json";

        jsonSerializer.sauvegarderEvenements(gestion.getTousLesEvenements(), filepath);

        List<Evenement> evenementsCharges = jsonSerializer.chargerEvenements(filepath);
        assertEquals(1, evenementsCharges.size());
        assertEquals("Tech Conference", evenementsCharges.get(0).getNom());

        // Nettoyage
        new File(filepath).delete();
    }

    @Test
    void testRechercheParNom() throws EvenementDejaExistantException {
        Conference conf1 = new Conference("C1", "Java Conference", LocalDateTime.now(), "Paris", 100, "Java");
        Conference conf2 = new Conference("C2", "Python Workshop", LocalDateTime.now(), "Lyon", 50, "Python");

        gestion.ajouterEvenement(conf1);
        gestion.ajouterEvenement(conf2);

        List<Evenement> resultats = gestion.rechercherParNom("Java");
        assertEquals(1, resultats.size());
        assertEquals("Java Conference", resultats.get(0).getNom());
    }

    @Test
    void testSingleton() {
        GestionEvenements instance1 = GestionEvenements.getInstance();
        GestionEvenements instance2 = GestionEvenements.getInstance();
        assertSame(instance1, instance2);
    }
}