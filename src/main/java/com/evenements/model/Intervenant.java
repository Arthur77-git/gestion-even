
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

    public Intervenant(String id, String nom, String specialite) {
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
}