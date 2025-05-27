package com.evenements.singleton;

import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.model.Organisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Singleton class for managing events, participants, and organizers.
 * Handles persistence to JSON files using Jackson.
 */
public class GestionEvenements {
    private static GestionEvenements instance;
    private List<Evenement> evenements;
    private List<Participant> allParticipants;
    private List<Organisateur> allOrganisateurs;
    private final File eventFile = new File("evenement.json");
    private final File participantFile = new File("participant.json");
    private final File organisateurFile = new File("organisateur.json");
    private final ObjectMapper mapper;

    private GestionEvenements() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        evenements = new ArrayList<>();
        allParticipants = new ArrayList<>();
        allOrganisateurs = new ArrayList<>();
        loadEventsFromFile();
        loadParticipantsFromFile();
        loadOrganisateursFromFile();
        associateEntities();
    }

    public static GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    private void loadEventsFromFile() {
        try {
            if (!eventFile.exists()) {
                evenements = new ArrayList<>();
                saveEventsToFile();
            } else {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Evenement.class);
                evenements = mapper.readValue(eventFile, listType);
            }
        } catch (IOException e) {
            evenements = new ArrayList<>();
            System.err.println("Error loading events from file: " + e.getMessage());
        }
    }

    private void loadParticipantsFromFile() {
        try {
            if (!participantFile.exists()) {
                allParticipants = new ArrayList<>();
                saveParticipantsToFile();
            } else {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Participant.class);
                allParticipants = mapper.readValue(participantFile, listType);
            }
        } catch (IOException e) {
            allParticipants = new ArrayList<>();
            System.err.println("Error loading participants from file: " + e.getMessage());
        }
    }

    private void loadOrganisateursFromFile() {
        try {
            if (!organisateurFile.exists()) {
                allOrganisateurs = new ArrayList<>();
                saveOrganisateursToFile();
            } else {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Organisateur.class);
                allOrganisateurs = mapper.readValue(organisateurFile, listType);
            }
        } catch (IOException e) {
            allOrganisateurs = new ArrayList<>();
            System.err.println("Error loading organizers from file: " + e.getMessage());
        }
    }

    private void associateEntities() {
        // Re-associate participants with events based on participantIds
        for (Evenement e : evenements) {
            List<Participant> eventParticipants = new ArrayList<>();
            for (String pid : e.getParticipantIds()) {
                Optional<Participant> p = allParticipants.stream().filter(part -> part.getId().equals(pid)).findFirst();
                p.ifPresent(eventParticipants::add);
            }
            e.setParticipants(eventParticipants);
        }
        // Re-associate organizers with events (if needed)
        // This could be extended based on your requirements
    }

    private void saveEventsToFile() {
        try {
            mapper.writeValue(eventFile, evenements);
        } catch (IOException e) {
            System.err.println("Error saving events to file: " + e.getMessage());
        }
    }

    private void saveParticipantsToFile() {
        try {
            mapper.writeValue(participantFile, allParticipants);
        } catch (IOException e) {
            System.err.println("Error saving participants to file: " + e.getMessage());
        }
    }

    private void saveOrganisateursToFile() {
        try {
            mapper.writeValue(organisateurFile, allOrganisateurs);
        } catch (IOException e) {
            System.err.println("Error saving organizers to file: " + e.getMessage());
        }
    }

    public void ajouterEvenement(Evenement e) {
        if (evenements.stream().anyMatch(event -> event.getId().equals(e.getId()))) {
            throw new RuntimeException("Un événement avec l'ID " + e.getId() + " existe déjà");
        }
        evenements.add(e);
        // Add new participants to global list
        for (Participant p : e.getParticipants()) {
            if (!allParticipants.contains(p)) {
                allParticipants.add(p);
            }
        }
        saveEventsToFile();
        saveParticipantsToFile();
    }

    public boolean supprimerEvenement(String id) {
        boolean removed = evenements.removeIf(e -> e.getId().equals(id));
        if (removed) {
            saveEventsToFile();
        }
        return removed;
    }

    public Optional<Evenement> rechercherEvenement(String id) {
        return evenements.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public List<Evenement> rechercherParNom(String nom) {
        return evenements.stream()
                .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Evenement> getTousLesEvenements() {
        return new ArrayList<>(evenements);
    }

    public List<Evenement> getEvenementsActifs() {
        return evenements.stream()
                .filter(e -> !e.isAnnule())
                .collect(Collectors.toList());
    }

    public int getNombreEvenements() {
        return evenements.size();
    }

    // Participant management
    public void ajouterParticipant(Participant p) {
        if (!allParticipants.contains(p)) {
            allParticipants.add(p);
            saveParticipantsToFile();
        }
    }

    public List<Participant> getAllParticipants() {
        return new ArrayList<>(allParticipants);
    }

    public Optional<Participant> rechercherParticipant(String id) {
        return allParticipants.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    // Organisateur management
    public void ajouterOrganisateur(Organisateur o) {
        if (!allOrganisateurs.contains(o)) {
            allOrganisateurs.add(o);
            saveOrganisateursToFile();
        }
    }

    public List<Organisateur> getAllOrganisateurs() {
        return new ArrayList<>(allOrganisateurs);
    }

    public Optional<Organisateur> rechercherOrganisateur(String id) {
        return allOrganisateurs.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * Clears all events, participants, and organizers, and saves the empty state.
     */
    public void viderTousLesEvenements() {
        evenements.clear();
        allParticipants.clear();
        allOrganisateurs.clear();
        saveEventsToFile();
        saveParticipantsToFile();
        saveOrganisateursToFile();
    }
}