package com.evenements.serialization;

import com.evenements.model.Evenement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonSerializer {
    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void sauvegarderEvenements(List<Evenement> evenements, String filepath) throws IOException {
        objectMapper.writeValue(new File(filepath), evenements);
    }

    public List<Evenement> chargerEvenements(String filepath) throws IOException {
        return objectMapper.readValue(new File(filepath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Evenement.class));
    }
}