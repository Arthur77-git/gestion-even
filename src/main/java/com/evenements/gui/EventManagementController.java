package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.evenements.singleton.GestionEvenements;
import com.evenements.exception.EvenementDejaExistantException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventManagementController {
    private GestionEvenements gestionEvenements;

    public EventManagementController() {
        this.gestionEvenements = GestionEvenements.getInstance();
    }

    public ObservableList<Evenement> getAllEvents() {
        return FXCollections.observableArrayList(gestionEvenements.getTousLesEvenements());
    }

    public void addEvent(Evenement event) throws EvenementDejaExistantException {
        gestionEvenements.ajouterEvenement(event);
    }

    public void updateEvent(Evenement event) {
        // Since GestionEvenements uses a Map, we can simply replace the event
        gestionEvenements.supprimerEvenement(event.getId());
        try {
            gestionEvenements.ajouterEvenement(event);
        } catch (EvenementDejaExistantException e) {
            // This should not happen as we removed the event first
        }
    }

    public void deleteEvent(String id) {
        gestionEvenements.supprimerEvenement(id);
    }
}