package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.ParticipantDejaInscritException;
import com.evenements.observer.EvenementObservable;
import com.evenements.observer.ParticipantObserver;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Conference.class, name = "conference"),
        @JsonSubTypes.Type(value = Concert.class, name = "concert")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Conference.class, Concert.class})
public abstract class Evenement implements EvenementObservable {
    @XmlElement
    protected String id;

    @XmlElement
    protected String nom;

    @XmlElement
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime date;

    @XmlElement
    protected String lieu;

    @XmlElement
    protected int capaciteMax;

    @XmlElement
    protected List<Participant> participants;

    protected List<ParticipantObserver> observers;

    protected boolean annule;

    public Evenement() {
        this.participants = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.annule = false;
    }

    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this();
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
    }

    public void ajouterParticipant(Participant participant) throws CapaciteMaxAtteinteException, ParticipantDejaInscritException {
        if (annule) {
            throw new IllegalStateException("Impossible d'ajouter un participant à un événement annulé");
        }
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement: " + nom);
        }
        if (participants.contains(participant)) {
            throw new ParticipantDejaInscritException("Le participant " + participant.getNom() + " est déjà inscrit");
        }
        participants.add(participant);
        ajouterObserver(participant);
        notifierObservers("Vous êtes inscrit à l'événement: " + nom);
    }

    public void supprimerParticipant(Participant participant) {
        if (participants.remove(participant)) {
            supprimerObserver(participant);
            notifierObservers("Vous avez été désinscrit de l'événement: " + nom);
        }
    }

    public void annuler() {
        this.annule = true;
        notifierObservers("L'événement " + nom + " a été annulé");
    }

    public void modifierEvenement(String nouveauNom, LocalDateTime nouvelleDate, String nouveauLieu) {
        if (!annule) {
            this.nom = nouveauNom != null ? nouveauNom : this.nom;
            this.date = nouvelleDate != null ? nouvelleDate : this.date;
            this.lieu = nouveauLieu != null ? nouveauLieu : this.lieu;
            notifierObservers("L'événement " + nom + " a été modifié");
        }
    }

    @Override
    public void ajouterObserver(ParticipantObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void supprimerObserver(ParticipantObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifierObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }

    public abstract void afficherDetails();

    // Getters et setters
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
    public List<Participant> getParticipants() { return new ArrayList<>(participants); }
    public boolean isAnnule() { return annule; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return Objects.equals(id, evenement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
