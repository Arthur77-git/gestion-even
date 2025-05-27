/**
 * Represents a concert event.
 */
package com.evenements.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@JsonTypeName("concert")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Concert extends Evenement {
    @XmlElement
    private String artiste;

    @XmlElement
    private String genreMusical;

    public Concert() {
        super();
    }

    /**
     * Constructs a Concert with the specified details.
     * @param id Unique identifier of the concert
     * @param nom Name of the concert
     * @param date Date and time of the concert
     * @param lieu Location of the concert
     * @param capaciteMax Maximum capacity of the concert
     * @param artiste Artist performing at the concert
     * @param genreMusical Musical genre of the concert
     * @throws IllegalArgumentException if artiste or genreMusical is invalid
     */
    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        if (artiste == null || artiste.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist cannot be null or empty");
        }
        if (genreMusical == null || genreMusical.trim().isEmpty()) {
            throw new IllegalArgumentException("Musical genre cannot be null or empty");
        }
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public void afficherDetails() {
        System.out.println("=== CONCERT ===");
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Date: " + date);
        System.out.println("Lieu: " + lieu);
        System.out.println("Artiste: " + artiste);
        System.out.println("Genre: " + genreMusical);
        System.out.println("Capacité: " + participants.size() + "/" + capaciteMax);
        System.out.println("Statut: " + (annule ? "ANNULÉ" : "ACTIF"));
    }

    // Getters et setters
    public String getArtiste() { return artiste; }
    public void setArtiste(String artiste) { this.artiste = artiste; }
    public String getGenreMusical() { return genreMusical; }
    public void setGenreMusical(String genreMusical) { this.genreMusical = genreMusical; }
}