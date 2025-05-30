package com.evenements.model;

public class Intervenant {
    private String nom;

    public Intervenant(String nom) {
        this.nom = nom;
    }

    public String getNom() { return nom; }

    public void setNom(String s) {
        this.nom = s;
    }
}