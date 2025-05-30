package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.singleton.GestionEvenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipantManagementController {
    private GestionEvenements gestionEvenements;

    public ParticipantManagementController() {
        this.gestionEvenements = GestionEvenements.getInstance();
    }

    public ObservableList<Participant> getParticipantsForEvent(String eventId) {
        Evenement event = gestionEvenements.getEvenementById(eventId);
        if (event != null) {
            return FXCollections.observableArrayList(event.getParticipants());
        }
        return FXCollections.observableArrayList();
    }

    public void addParticipantToEvent(String eventId, Participant participant) {
        Evenement event = gestionEvenements.getEvenementById(eventId);
        if (event != null) {
            event.getParticipants().add(participant);
        }
    }

    public void removeParticipantFromEvent(String eventId, Participant participant) {
        Evenement event = gestionEvenements.getEvenementById(eventId);
        if (event != null) {
            event.getParticipants().remove(participant);
        }
    }

    public ObservableList<Evenement> getAllEvents() {
        return FXCollections.observableArrayList(gestionEvenements.getTousLesEvenements());
    }
}