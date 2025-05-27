/**
 * Represents a conference event.
 */
package com.evenements.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("conference")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Conference extends Evenement {
    @XmlElement
    private String theme;

    @XmlElement
    private List<Intervenant> intervenants;

    public Conference() {
        super();
        this.intervenants = new ArrayList<>();
    }

    /**
     * Constructs a Conference with the specified details.
     * @param id Unique identifier of the conference
     * @param nom Name of the conference
     * @param date Date and time of the conference
     * @param lieu Location of the conference
     * @param capaciteMax Maximum capacity of the conference
     * @param theme Theme of the conference
     * @throws IllegalArgumentException if theme is invalid
     */
    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        if (theme == null || theme.trim().isEmpty()) {
            throw new IllegalArgumentException("Theme cannot be null or empty");
        }
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== CONFÉRENCE ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Date: " + date);
        System.out.println("Lieu: " + lieu);
        System.out.println("Thème: " + theme);
        System.out.println("Capacité: " + participants.size() + "/" + capaciteMax);
        System.out.println("Statut: " + (annule ? "ANNULÉ" : "ACTIF"));
        if (!intervenants.isEmpty()) {
            System.out.println("Intervenants:");
            intervenants.stream().forEach(i -> System.out.println("- " + i.getNom()));
        }
    }

    public void ajouterIntervenant(Intervenant intervenant) {
        if (!intervenants.contains(intervenant)) {
            intervenants.add(intervenant);
        }
    }

    // Getters et setters
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public List<Intervenant> getIntervenants() { return new ArrayList<>(intervenants); }
    public void setIntervenants(List<Intervenant> intervenants) { this.intervenants = intervenants; }
}