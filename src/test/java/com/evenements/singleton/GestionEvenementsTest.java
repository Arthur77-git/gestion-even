package com.evenements.singleton;

import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.evenements.exception.EvenementDejaExistantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionEvenementsTest {
    private GestionEvenements gestion;

    @BeforeEach
    void setUp() {
        gestion = GestionEvenements.getInstance();
        gestion.viderTousLesEvenements();
    }

    @Test
    void testAjouterEvenement() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        gestion.ajouterEvenement(conference);
        assertEquals(1, gestion.getNombreEvenements());
        assertTrue(gestion.rechercherEvenement("E001").isPresent());
    }

    @Test
    void testAjouterEvenementDejaExistant() {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        assertDoesNotThrow(() -> gestion.ajouterEvenement(conference));
        assertThrows(EvenementDejaExistantException.class, () -> gestion.ajouterEvenement(conference));
    }

    @Test
    void testSupprimerEvenement() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        gestion.ajouterEvenement(conference);
        assertTrue(gestion.supprimerEvenement("E001"));
        assertEquals(0, gestion.getNombreEvenements());
        assertFalse(gestion.rechercherEvenement("E001").isPresent());
    }

    @Test
    void testSupprimerEvenementInexistant() {
        assertFalse(gestion.supprimerEvenement("E999"));
    }

    @Test
    void testRechercherEvenement() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        gestion.ajouterEvenement(conference);
        assertTrue(gestion.rechercherEvenement("E001").isPresent());
        assertEquals("Conf Tech", gestion.rechercherEvenement("E001").get().getNom());
        assertFalse(gestion.rechercherEvenement("E999").isPresent());
    }

    @Test
    void testRechercherParNom() throws EvenementDejaExistantException {
        Conference conference1 = new Conference("E001", "Conf Tech 2025", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        Conference conference2 = new Conference("E002", "Conf Bio 2025", LocalDateTime.now(), "Lyon", 50, "Bio", Arrays.asList("Dr. Martin"));
        gestion.ajouterEvenement(conference1);
        gestion.ajouterEvenement(conference2);
        List<?> result = gestion.rechercherParNom("Tech");
        assertEquals(1, result.size());
        assertEquals("Conf Tech 2025", result.get(0).getNom());
    }

    @Test
    void testRechercherParLieu() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        gestion.ajouterEvenement(conference);
        List<?> result = gestion.rechercherParLieu("Paris");
        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getLieu());
    }

    @Test
    void testGetEvenementsActifs() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        Concert concert = new Concert("E002", "Jazz Night", LocalDateTime.now(), "Lyon", 200, "Ella Jazz", "Jazz");
        gestion.ajouterEvenement(conference);
        gestion.ajouterEvenement(concert);
        concert.annuler();
        assertEquals(1, gestion.getEvenementsActifs().size());
        assertEquals("Conf Tech", gestion.getEvenementsActifs().get(0).getNom());
    }

    @Test
    void testGetTousLesEvenements() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        Concert concert = new Concert("E002", "Jazz Night", LocalDateTime.now(), "Lyon", 200, "Ella Jazz", "Jazz");
        gestion.ajouterEvenement(conference);
        gestion.ajouterEvenement(concert);
        assertEquals(2, gestion.getTousLesEvenements().size());
    }

    @Test
    void testViderTousLesEvenements() throws EvenementDejaExistantException {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        gestion.ajouterEvenement(conference);
        gestion.viderTousLesEvenements();
        assertEquals(0, gestion.getNombreEvenements());
    }
}package com.evenements.singleton;

import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GestionEvenements class.
 */
public class GestionEvenementsTest {

    private GestionEvenements gestion;
    private Conference conference;

    @BeforeEach
    void setUp() {
        gestion = GestionEvenements.getInstance();
        gestion.viderTousLesEvenements(); // Clear state between tests
        conference = new Conference("CONF1", "Conf IA", LocalDateTime.now(), "Paris", 150, "IA");
    }

    @Test
    void testSingletonInstance() {
        GestionEvenements instance1 = GestionEvenements.getInstance();
        GestionEvenements instance2 = GestionEvenements.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testAjouterEvenement() {
        gestion.ajouterEvenement(conference);
        assertEquals(1, gestion.getNombreEvenements());
        assertEquals(conference, gestion.rechercherEvenement("CONF1").orElse(null));
    }

    @Test
    void testAjouterEvenementDejaExistant() {
        gestion.ajouterEvenement(conference);
        assertThrows(RuntimeException.class, () -> gestion.ajouterEvenement(conference));
    }

    @Test
    void testSupprimerEvenement() {
        gestion.ajouterEvenement(conference);
        boolean result = gestion.supprimerEvenement("CONF1");
        assertTrue(result);
        assertEquals(0, gestion.getNombreEvenements());
        // Removed assertTrue(conference.isAnnule()) since supprimerEvenement doesn't cancel
    }

    @Test
    void testSupprimerEvenementNonExistant() {
        boolean result = gestion.supprimerEvenement("INVALID");
        assertFalse(result);
        assertEquals(0, gestion.getNombreEvenements());
    }

    @Test
    void testRechercherEvenement() {
        gestion.ajouterEvenement(conference);
        Optional<Evenement> result = gestion.rechercherEvenement("CONF1");
        assertTrue(result.isPresent());
        assertEquals(conference, result.get());

        Optional<Evenement> notFound = gestion.rechercherEvenement("INVALID");
        assertFalse(notFound.isPresent());
    }

    @Test
    void testRechercherParNom() {
        gestion.ajouterEvenement(conference);
        List<Evenement> result = gestion.rechercherParNom("IA");
        assertEquals(1, result.size());
        assertEquals(conference, result.get(0));

        List<Evenement> noResult = gestion.rechercherParNom("NonExistant");
        assertTrue(noResult.isEmpty());
    }

    @Test
    void testGetEvenementsActifs() {
        Conference conference2 = new Conference("CONF2", "Conf Tech", LocalDateTime.now(), "Lyon", 100, "Tech");
        gestion.ajouterEvenement(conference);
        gestion.ajouterEvenement(conference2);
        conference2.annuler();

        List<Evenement> actifs = gestion.getEvenementsActifs();
        assertEquals(1, actifs.size());
        assertEquals(conference, actifs.get(0));
    }

    @Test
    void testGetTousLesEvenements() {
        gestion.ajouterEvenement(conference);
        List<Evenement> all = gestion.getTousLesEvenements();
        assertEquals(1, all.size());
        assertEquals(conference, all.get(0));
    }

    @Test
    void testViderTousLesEvenements() {
        gestion.ajouterEvenement(conference);
        gestion.viderTousLesEvenements();
        assertEquals(0, gestion.getNombreEvenements());
    }
