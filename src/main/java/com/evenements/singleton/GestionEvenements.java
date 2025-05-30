package com.evenements.singleton;

import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GestionEvenements {
    private static GestionEvenements instance;
    private Map<String, Evenement> evenements;
    private ObjectMapper mapper;

    private GestionEvenements() {
        evenements = new HashMap<>();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        loadEventsFromFile();
    }

    public static GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    private void loadEventsFromFile() {
        File file = new File("evenements.json");
        if (file.exists()) {
            try {
                Evenement[] loadedEvents = mapper.readValue(file, Evenement[].class);
                evenements.clear();
                for (Evenement event : loadedEvents) {
                    evenements.put(event.getId(), event);
                }
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des événements: " + e.getMessage());
            }
        }
    }

    private void saveEventsToFile() {
        try {
            mapper.writeValue(new File("evenements.json"), evenements.values());
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des événements: " + e.getMessage());
        }
    }

    public void ajouterEvenement(Evenement evenement) throws EvenementDejaExistantException {
        if (evenements.containsKey(evenement.getId())) {
            throw new EvenementDejaExistantException("Un événement avec l'ID " + evenement.getId() + " existe déjà.");
        }
        evenements.put(evenement.getId(), evenement);
        saveEventsToFile();
    }

    public void supprimerEvenement(String id) {
        evenements.remove(id);
        saveEventsToFile();
    }

    public List<Evenement> getTousLesEvenements() {
        return new ArrayList<>(evenements.values());
    }

    public int getNombreEvenements() {
        return evenements.size();
    }

    public List<Evenement> getEvenementsActifs() {
        return evenements.values().stream()
                .filter(e -> !e.isAnnule())
                .collect(Collectors.toList());
    }

    public Evenement getEvenementById(String id) {
        return evenements.get(id);
    }
}