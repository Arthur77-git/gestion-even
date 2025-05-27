package com.evenements.serialization;

import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the XmlSerializer class.
 */
public class XmlSerializerTest {

    @Test
    void testSauvegarderEtChargerEvenements() throws Exception {
        XmlSerializer serializer = new XmlSerializer();
        Conference conference = new Conference("CONF1", "Conf IA", LocalDateTime.now(), "Paris", 150, "IA");
        List<Evenement> evenements = Arrays.asList(conference);

        serializer.sauvegarderEvenements(evenements, "test.xml");
        List<Evenement> loaded = serializer.chargerEvenements("test.xml");

        assertEquals(1, loaded.size());
        Conference loadedConference = (Conference) loaded.get(0);
        assertEquals("CONF1", loadedConference.getId());
        assertEquals("Conf IA", loadedConference.getNom());
        assertEquals("Paris", loadedConference.getLieu());
        assertEquals(150, loadedConference.getCapaciteMax());
        assertEquals("IA", loadedConference.getTheme());
    }
}