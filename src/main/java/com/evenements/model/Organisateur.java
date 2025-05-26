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

    public Organisateur(String id, String nom, String email, NotificationService notificationService) {
        super(id, nom, email, notificationService);
        this.evenementsOrganisesIds = new ArrayList<>();
    }

    public void ajouterEvenementOrganise(String evenementId) {
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
}