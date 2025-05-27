package com.evenements.strategy;

import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EvenementSorter class.
 */
public class EvenementSorterTest {

    private List<Evenement> evenements;

    @BeforeEach
    void setUp() {
        Conference conf1 = new Conference("CONF1", "Conf A", LocalDateTime.now().plusDays(1), "Paris", 150, "IA");
        Conference conf2 = new Conference("CONF2", "Conf B", LocalDateTime.now(), "Lyon", 100, "Tech");
        Conference conf3 = new Conference("CONF3", "Conf C", LocalDateTime.now().plusDays(2), "Marseille", 200, "Data");
        evenements = Arrays.asList(conf1, conf2, conf3);
    }

    @Test
    void testTriParNomStrategy() {
        EvenementSorter sorter = new EvenementSorter(new TriParNomStrategy());
        List<Evenement> sorted = sorter.trier(evenements);
        assertEquals("Conf A", sorted.get(0).getNom());
        assertEquals("Conf B", sorted.get(1).getNom());
        assertEquals("Conf C", sorted.get(2).getNom());
    }

    @Test
    void testTriParDateStrategy() {
        EvenementSorter sorter = new EvenementSorter(new TriParDateStrategy());
        List<Evenement> sorted = sorter.trier(evenements);
        assertEquals("Conf B", sorted.get(0).getNom()); // Earliest date
        assertEquals("Conf A", sorted.get(1).getNom());
        assertEquals("Conf C", sorted.get(2).getNom());
    }

    @Test
    void testTriParCapaciteStrategy() {
        EvenementSorter sorter = new EvenementSorter(new TriParCapaciteStrategy());
        List<Evenement> sorted = sorter.trier(evenements);
        assertEquals(200, sorted.get(0).getCapaciteMax()); // Conf C
        assertEquals(150, sorted.get(1).getCapaciteMax()); // Conf A
        assertEquals(100, sorted.get(2).getCapaciteMax()); // Conf B
    }

    @Test
    void testSetStrategy() {
        EvenementSorter sorter = new EvenementSorter(new TriParNomStrategy());
        sorter.setStrategy(new TriParCapaciteStrategy());
        List<Evenement> sorted = sorter.trier(evenements);
        assertEquals(200, sorted.get(0).getCapaciteMax()); // Conf C
    }
}