package com.evenements.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Conference.class, name = "Conference"),
        @JsonSubTypes.Type(value = Concert.class, name = "Concert"),
        @JsonSubTypes.Type(value = Evenement.class, name = "Evenement")
})
public abstract class Evenement {
    private String id;
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private boolean annule;
    private List<Participant> participants;

    public Evenement() {
        this.participants = new ArrayList<>();
    }

    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.annule = false;
        this.participants = new ArrayList<>();
    }

    public abstract void afficherDetails();

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public boolean isAnnule() { return annule; }
    public void setAnnule(boolean annule) { this.annule = annule; }
    public List<Participant> getParticipants() { return participants; }
    public void setParticipants(List<Participant> participants) { this.participants = participants; }
}