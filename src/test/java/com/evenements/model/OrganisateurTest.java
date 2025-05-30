package com.evenements.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrganisateurTest {
    private Organisateur organisateur;

    @BeforeEach
    void setUp() {
        organisateur = new Organisateur("O001", "Alice Dupont", "alice@email.com", "EventCorp");
    }

    @Test
    void testGetOrganisation() {
        assertEquals("EventCorp", organisateur.getOrganisation());
    }

    @Test
    void testSetOrganisation() {
        organisateur.setOrganisation("NewCorp");
        assertEquals("NewCorp", organisateur.getOrganisation());
    }

    @Test
    void testEmptyOrganisateur() {
        Organisateur emptyOrganisateur = new Organisateur("O002", "Bob Martin", "bob@email.com", "Corp");
        assertEquals("Corp", emptyOrganisateur.getOrganisation());
    }

    @Test
    void testInvalidOrganisation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Organisateur("O003", "Charlie", "charlie@email.com", "");
        });
        assertEquals("L'organisation ne peut pas être vide", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Organisateur("O004", "David", "david@email.com", null);
        });
        assertEquals("L'organisation ne peut pas être vide", exception.getMessage());
    }

    @Test
    void testGetNomEmail() {
        Organisateur org = new Organisateur("O005", "Eve", "eve@email.com", "TechEvents");
        assertEquals("Eve", org.getNom());
        assertEquals("eve@email.com", org.getEmail());
    }
}