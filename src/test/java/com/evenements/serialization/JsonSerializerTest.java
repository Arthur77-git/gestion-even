package com.evenements.serialization;

import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testSerializeDeserializeConference() throws Exception {
        Conference conference = new Conference("E001", "Conf Tech", LocalDateTime.now(), "Paris", 100, "IA", Arrays.asList("Dr. Dupont"));
        File file = new File("test_conference.json");
        mapper.writeValue(file, conference);
        Conference deserialized = mapper.readValue(file, Conference.class);
        assertEquals("E001", deserialized.getId());
        assertEquals("Conf Tech", deserialized.getNom());
        assertEquals("IA", deserialized.getTheme());
        file.delete();
    }

    @Test
    void testSerializeDeserializeConcert() throws Exception {
        Concert concert = new Concert("E002", "Jazz Night", LocalDateTime.now(), "Lyon", 200, "Ella Jazz", "Jazz");
        File file = new File("test_concert.json");
        mapper.writeValue(file, concert);
        Concert deserialized = mapper.readValue(file, Concert.class);
        assertEquals("E002", deserialized.getId());
        assertEquals("Jazz Night", deserialized.getNom());
        assertEquals("Ella Jazz", deserialized.getArtiste());
        file.delete();
    }
}