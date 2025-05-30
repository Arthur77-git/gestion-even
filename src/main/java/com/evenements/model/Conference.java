package com.evenements.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conference extends Evenement {
    private String theme;
    private List<String> intervenants;

    public Conference() {
        super();
        this.intervenants = new ArrayList<>();
    }

    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme, List<String> intervenants) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>(intervenants);
    }

    @Override
    public void afficherDetails() {
        System.out.println("Conférence: " + getNom() + ", Thème: " + theme + ", Intervenants: " + intervenants);
    }

    // Getters and Setters
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public List<String> getIntervenants() { return intervenants; }
    public void setIntervenants(List<String> intervenants) { this.intervenants = new ArrayList<>(intervenants); }
}