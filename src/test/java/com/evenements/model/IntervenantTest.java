package com.evenements.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervenantTest {
    @Test
    void testIntervenantCreation() {
        Intervenant intervenant = new Intervenant("Dr. Dupont");
        assertEquals("Dr. Dupont", intervenant.getNom());
    }

    @Test
    void testEmptyIntervenant() {
        Intervenant emptyIntervenant = new Intervenant("");
        assertEquals("", emptyIntervenant.getNom());
    }

    @Test
    void testSetNom() {
        Intervenant intervenant = new Intervenant("Dr. Dupont");
        intervenant.setNom("Dr. Martin");
        assertEquals("Dr. Martin", intervenant.getNom());
    }

    @Test
    void testInvalidNom() {
        assertThrows(IllegalArgumentException.class, () -> new Intervenant(null));
    }
}