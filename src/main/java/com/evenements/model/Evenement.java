package com.evenements.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for events in the event management system.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Concert.class, name = "Concert"),
        @JsonSubTypes.Type(value = Conference.class, name = "Conference")
})
public abstract class Evenement {
    private String id;
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private boolean annule;
    private List<String> participantIds = new ArrayList<>(); // Store participant IDs
    @JsonIgnore
    private List<Participant> participants = new ArrayList<>(); // Runtime reference, not serialized

    protected Evenement() {}

    protected Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.annule = false;
    }

    public void modifierEvenement(String nom, LocalDateTime date, String lieu) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    public boolean ajouterParticipant(Participant p) {
        if (participants.size() >= capaciteMax) {
            throw new IllegalStateException("Capacité maximale atteinte");
        }
        if (participants.contains(p)) {
            return false;
        }
        participants.add(p);
        participantIds.add(p.getId());
        return true;
    }

    public void annuler() {
        this.annule = true;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public LocalDateTime getDate() { return date; }
    public String getLieu() { return lieu; }
    public int getCapaciteMax() { return capaciteMax; }
    public boolean isAnnule() { return annule; }
    @JsonIgnore
    public List<Participant> getParticipants() { return new ArrayList<>(participants); }
    public List<String> getParticipantIds() { return new ArrayList<>(participantIds); }
    public void setId(String id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public void setAnnule(boolean annule) { this.annule = annule; }
    public void setParticipantIds(List<String> participantIds) { this.participantIds = new ArrayList<>(participantIds); }
    public void setParticipants(List<Participant> participants) { this.participants = new ArrayList<>(participants); }
}