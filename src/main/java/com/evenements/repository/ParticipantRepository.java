package com.evenements.repository;

import com.evenements.model.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantRepository {
    private Map<String, Participant> participants;
    private ObjectMapper mapper;

    public ParticipantRepository() {
        participants = new HashMap<>();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        loadParticipants();
    }

    private void loadParticipants() {
        try {
            File file = new File("participants.json");
            if (file.exists()) {
                Participant[] loaded = mapper.readValue(file, Participant[].class);
                for (Participant p : loaded) {
                    participants.put(p.getId(), p);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des participants: " + e.getMessage());
        }
    }

    public void saveParticipants() {
        try {
            mapper.writeValue(new File("participants.json"), participants.values());
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des participants: " + e.getMessage());
        }
    }

    public void addParticipant(Participant participant) {
        participants.put(participant.getId(), participant);
        saveParticipants();
    }

    public boolean removeParticipant(String id) {
        if (participants.remove(id) != null) {
            saveParticipants();
            return true;
        }
        return false;
    }

    public Participant getParticipant(String id) {
        return participants.get(id);
    }

    public List<Participant> getAllParticipants() {
        return new ArrayList<>(participants.values());
    }
}