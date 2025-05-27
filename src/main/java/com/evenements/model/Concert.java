package com.evenements.model;

import java.time.LocalDateTime;

/**
 * Represents a concert event, extending the base Evenement class.
 */
public class Concert extends Evenement {
    private String artiste;
    private String genre;

    public Concert() {
        super();
    }

    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genre) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genre = genre;
    }

    public String getArtiste() { return artiste; }
    public void setArtiste(String artiste) { this.artiste = artiste; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}