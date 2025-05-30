package com.evenements.factory;

import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.evenements.model.Evenement;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EvenementFactoryTest {
    @Test
    void testCreateConference() {
        Evenement evenement = EvenementFactory.createEvenement(
                "conference", "E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", "Dr. Dupont,Dr. Martin"
        );
        assertTrue(evenement instanceof Conference);
        Conference conference = (Conference) evenement;
        assertEquals("E001", conference.getId());
        assertEquals("Conf Tech", conference.getNom());
        assertEquals("IA", conference.getTheme());
        assertEquals(2, conference.getIntervenants().size());
    }

    @Test
    void testCreateConcert() {
        Evenement evenement = EvenementFactory.createEvenement(
                "concert", "E002", "Jazz Night", LocalDateTime.now(), "Lyon", 200, "Ella Jazz", "Jazz"
        );
        assertTrue(evenement instanceof Concert);
        Concert concert = (Concert) evenement;
        assertEquals("E002", concert.getId());
        assertEquals("Jazz Night", concert.getNom());
        assertEquals("Ella Jazz", concert.getArtiste());
        assertEquals("Jazz", concert.getGenreMusical());
    }

    @Test
    void testCreateDefaultEvenement() {
        Evenement evenement = EvenementFactory.createEvenement(
                "autre", "E003", "Generic Event", LocalDateTime.now(), "Nantes", 50
        );
        assertNotNull(evenement);
        assertEquals("E003", evenement.getId());
        assertEquals("Generic Event", evenement.getNom());
    }
}