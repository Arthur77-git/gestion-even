package com.evenements.singleton;

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
}