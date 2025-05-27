package com.evenements.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an organizer, a special type of participant who manages events.
 */
public class Organisateur extends Participant {
    private List<String> evenementsOrganisesIds;

    public Organisateur() {
        super();
        this.evenementsOrganisesIds = new ArrayList<>();
    }

    public Organisateur(String id, String nom, String email) {
        super(id, nom, email);
        this.evenementsOrganisesIds = new ArrayList<>();
    }

    public void ajouterEvenementOrganise(String evenementId) {
        if (evenementId == null || evenementId.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
        if (!evenementsOrganisesIds.contains(evenementId)) {
            evenementsOrganisesIds.add(evenementId);
        }
    }

    public void supprimerEvenementOrganise(String evenementId) {
        evenementsOrganisesIds.remove(evenementId);
    }

    public List<String> getEvenementsOrganisesIds() {
        return new ArrayList<>(evenementsOrganisesIds); // Return a copy for immutability
    }

    public void setEvenementsOrganisesIds(List<String> evenementsOrganisesIds) {
        this.evenementsOrganisesIds = new ArrayList<>(evenementsOrganisesIds); // Create a new list to ensure immutability
    }

    @Override
    public String toString() {
        return "Organisateur{id='" + getId() + "', nom='" + getNom() + "', evenementsOrganisesIds=" + evenementsOrganisesIds + "}";
    }
}