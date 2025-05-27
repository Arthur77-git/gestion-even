/**
 * Represents an organizer, a special type of participant who manages events.
 */
package com.evenements.model;

import com.evenements.service.NotificationService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Organisateur extends Participant {
    @XmlElement
    private List<String> evenementsOrganisesIds;

    public Organisateur() {
        super();
        this.evenementsOrganisesIds = new ArrayList<>();
    }

    /**
     * Constructs an Organisateur with the specified details.
     * @param id Unique identifier of the organizer
     * @param nom Name of the organizer
     * @param email Email address of the organizer
     * @param notificationService Service to send notifications
     */
    public Organisateur(String id, String nom, String email, NotificationService notificationService) {
        super(id, nom, email, notificationService);
        this.evenementsOrganisesIds = new ArrayList<>();
    }

    /**
     * Adds an event to the list of organized events.
     * @param evenementId ID of the event to add
     * @throws IllegalArgumentException if evenementId is null or empty
     */
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
        return new ArrayList<>(evenementsOrganisesIds);
    }

    public void setEvenementsOrganisesIds(List<String> evenementsOrganisesIds) {
        this.evenementsOrganisesIds = evenementsOrganisesIds;
    }

    @Override
    public String toString() {
        return "Organisateur{id='" + getId() + "', nom='" + getNom() + "', evenementsOrganisesIds=" + evenementsOrganisesIds + "}";
    }
}