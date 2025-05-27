package com.evenements.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntervenantTest {

    @Test
    void testConstructorAndGetters() {
        Intervenant intervenant = new Intervenant("INT1", "Dr. Marie", "Machine Learning");
        assertEquals("INT1", intervenant.getId());
        assertEquals("Dr. Marie", intervenant.getNom());
        assertEquals("Machine Learning", intervenant.getSpecialite());
    }

    @Test
    void testDefaultConstructor() {
        Intervenant emptyIntervenant = new Intervenant();
        assertNull(emptyIntervenant.getId());
        assertNull(emptyIntervenant.getNom());
        assertNull(emptyIntervenant.getSpecialite());
    }

    @Test
    void testSetters() {
        Intervenant intervenant = new Intervenant("INT1", "Dr. Marie", "Machine Learning");
        intervenant.setId("INT2");
        intervenant.setNom("Prof. Jean");
        intervenant.setSpecialite("Réseaux de Neurones");

        assertEquals("INT2", intervenant.getId());
        assertEquals("Prof. Jean", intervenant.getNom());
        assertEquals("Réseaux de Neurones", intervenant.getSpecialite());
    }

    @Test
    void testConstructorWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Intervenant(null, "Dr. Marie", "Machine Learning"));
        assertThrows(IllegalArgumentException.class, () -> new Intervenant("INT1", null, "Machine Learning"));
        assertThrows(IllegalArgumentException.class, () -> new Intervenant("INT1", "Dr. Marie", ""));
    }

    @Test
    void testEqualsAndHashCode() {
        Intervenant intervenant1 = new Intervenant("INT1", "Dr. Marie", "Machine Learning");
        Intervenant intervenant2 = new Intervenant("INT1", "Prof. Jean", "Réseaux de Neurones");
        Intervenant intervenant3 = new Intervenant("INT2", "Dr. Marie", "Machine Learning");

        assertEquals(intervenant1, intervenant2);
        assertEquals(intervenant1.hashCode(), intervenant2.hashCode());
        assertNotEquals(intervenant1, intervenant3);
        assertNotEquals(intervenant1.hashCode(), intervenant3.hashCode());
    }

    @Test
    void testToString() {
        Intervenant intervenant = new Intervenant("INT1", "Dr. Marie", "Machine Learning");
        String expected = "Intervenant{id='INT1', nom='Dr. Marie', specialite='Machine Learning'}";
        assertEquals(expected, intervenant.toString());
    }
}