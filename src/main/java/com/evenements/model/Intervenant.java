/**
 * Represents a speaker at a conference.
 */
package com.evenements.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Intervenant {
    @XmlElement
    private String id;

    @XmlElement
    private String nom;

    @XmlElement
    private String specialite;

    public Intervenant() {}

    /**
     * Constructs an Intervenant with the specified details.
     * @param id Unique identifier of the intervenant
     * @param nom Name of the intervenant
     * @param specialite Specialty of the intervenant
     * @throws IllegalArgumentException if id, nom, or specialite is invalid
     */
    public Intervenant(String id, String nom, String specialite) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (specialite == null || specialite.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty cannot be null or empty");
        }
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intervenant that = (Intervenant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Intervenant{id='" + id + "', nom='" + nom + "', specialite='" + specialite + "'}";
    }
}