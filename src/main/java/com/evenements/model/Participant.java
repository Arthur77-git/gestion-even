package com.evenements.model;

import com.evenements.observer.ParticipantObserver;
import com.evenements.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Participant implements ParticipantObserver {
    @XmlElement
    private String id;

    @XmlElement
    private String nom;

    @XmlElement
    private String email;

    @XmlTransient
    @JsonIgnore
    private NotificationService notificationService;

    public Participant() {}

    public Participant(String id, String nom, String email, NotificationService notificationService) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.notificationService = notificationService;
    }

    @Override
    public void update(String message) {
        if (notificationService != null) {
            notificationService.envoyerNotification("Notification pour " + nom + " (" + email + "): " + message);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}