package com.evenements.model;

import java.util.ArrayList;
import java.util.List;

public class Participant {
    private String id;
    private String nom;
    private String email;
    private List<String> eventIds;

    public Participant(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.eventIds = new ArrayList<>();
    }

    public void registerToEvent(String eventId) {
        eventIds.add(eventId);
    }

    public void unregisterFromEvent(String eventId) {
        eventIds.remove(eventId);
    }

    // Getters and setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public List<String> getEventIds() { return eventIds; }
}