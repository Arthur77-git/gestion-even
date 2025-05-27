package com.evenements.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantTest {

    private Participant participant;
    private final String id = "1";
    private final String nom = "John Doe";
    private final String email = "john@example.com";

    @BeforeEach
    void setUp() {
        participant = new Participant(id, nom, email);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(id, participant.getId());
        assertEquals(nom, participant.getNom());
        assertEquals(email, participant.getEmail());
    }

    @Test
    void testDefaultConstructor() {
        Participant emptyParticipant = new Participant();
        assertNull(emptyParticipant.getId());
        assertNull(emptyParticipant.getNom());
        assertNull(emptyParticipant.getEmail());
    }

    @Test
    void testSetters() {
        String newId = "2";
        String newNom = "Jane Doe";
        String newEmail = "jane@example.com";

        participant.setId(newId);
        participant.setNom(newNom);
        participant.setEmail(newEmail);

        assertEquals(newId, participant.getId());
        assertEquals(newNom, participant.getNom());
        assertEquals(newEmail, participant.getEmail());
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(participant.equals(participant));
    }

    @Test
    void testEqualsSameId() {
        Participant other = new Participant(id, "Different Name", "different@example.com");
        assertTrue(participant.equals(other));
    }

    @Test
    void testEqualsDifferentId() {
        Participant other = new Participant("2", nom, email);
        assertFalse(participant.equals(other));
    }

    @Test
    void testEqualsNull() {
        assertFalse(participant.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(participant.equals(new Object()));
    }

    @Test
    void testHashCodeConsistency() {
        Participant other = new Participant(id, "Different Name", "different@example.com");
        assertEquals(participant.hashCode(), other.hashCode());
    }

    @Test
    void testHashCodeDifferentId() {
        Participant other = new Participant("2", nom, email);
        assertNotEquals(participant.hashCode(), other.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Participant{id='1', nom='John Doe', email='john@example.com'}";
        assertEquals(expected, participant.toString());
    }

    @Test
    void testConstructorWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(null, nom, email));
        assertThrows(IllegalArgumentException.class, () -> new Participant("", nom, email));
    }

    @Test
    void testConstructorWithInvalidNom() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, null, email));
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, "", email));
    }

    @Test
    void testConstructorWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, nom, "invalid-email"));
        assertThrows(IllegalArgumentException.class, () -> new Participant(id, nom, null));
    }
}