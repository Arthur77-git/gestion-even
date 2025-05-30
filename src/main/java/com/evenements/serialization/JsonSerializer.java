package com.evenements.serialization;

import com.evenements.model.Evenement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.evenements.model.Evenement;

public class JsonSerializer {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static void sauvegarderEvenements(Map<String, Evenement> evenements, String filepath) throws IOException {
        objectMapper.writerFor(new TypeReference<Map<String, Evenement>>() {
        }).withDefaultPrettyPrinter().writeValue(new File(filepath), evenements);
    }

    public static Map<String, Evenement> chargerEvenements(String filepath) throws IOException {
        return objectMapper.readValue(new File(filepath),
                new TypeReference<Map<String, Evenement>>() {
                });
    }
}