package com.evenements.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisateurTest {

    private Organisateur organisateur;

    @BeforeEach
    void setUp() {
        organisateur = new Organisateur("ORG1", "Claire Admin", "admin@events.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("ORG1", organisateur.getId());
        assertEquals("Claire Admin", organisateur.getNom());
        assertEquals("admin@events.com", organisateur.getEmail());
        assertTrue(organisateur.getEvenementsOrganisesIds().isEmpty());
    }

    @Test
    void testDefaultConstructor() {
        Organisateur emptyOrganisateur = new Organisateur();
        assertNull(emptyOrganisateur.getId());
        assertNull(emptyOrganisateur.getNom());
        assertNull(emptyOrganisateur.getEmail());
        assertTrue(emptyOrganisateur.getEvenementsOrganisesIds().isEmpty());
    }

    @Test
    void testAjouterEvenementOrganise() {
        organisateur.ajouterEvenementOrganise("EVT1");
        List<String> evenements = organisateur.getEvenementsOrganisesIds();
        assertEquals(1, evenements.size());
        assertEquals("EVT1", evenements.get(0));

        // Adding the same event ID should not duplicate
        organisateur.ajouterEvenementOrganise("EVT1");
        assertEquals(1, evenements.size());
    }

    @Test
    void testAjouterEvenementOrganiseWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> organisateur.ajouterEvenementOrganise(null));
        assertThrows(IllegalArgumentException.class, () -> organisateur.ajouterEvenementOrganise(""));
    }

    @Test
    void testSupprimerEvenementOrganise() {
        organisateur.ajouterEvenementOrganise("EVT1");
        organisateur.supprimerEvenementOrganise("EVT1");
        assertTrue(organisateur.getEvenementsOrganisesIds().isEmpty());
    }

    @Test
    void testToString() {
        organisateur.ajouterEvenementOrganise("EVT1");
        String expected = "Organisateur{id='ORG1', nom='Claire Admin', evenementsOrganisesIds=[EVT1]}";
        assertEquals(expected, organisateur.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Organisateur other = new Organisateur("ORG1", "Different Name", "different@events.com");
        assertEquals(organisateur, other);
        assertEquals(organisateur.hashCode(), other.hashCode());

        Organisateur different = new Organisateur("ORG2", "Claire Admin", "admin@events.com");
        assertNotEquals(organisateur, different);
        assertNotEquals(organisateur.hashCode(), different.hashCode());
    }
}