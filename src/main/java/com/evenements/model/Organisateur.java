package com.evenements.model;

public class Organisateur extends Participant {
    private String organisation;

    public Organisateur(String id, String nom, String email, String organisation) {
        super(id, nom, email);
        this.organisation = organisation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
}