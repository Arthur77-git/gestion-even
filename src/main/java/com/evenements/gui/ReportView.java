package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.singleton.GestionEvenements;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ReportView {
    private ScrollPane root;
    private GestionEvenements gestion;

    public ReportView() {
        gestion = GestionEvenements.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Rapport des Événements"),
                new Label("Nombre total d'événements: " + gestion.getNombreEvenements()),
                new Label("Événements actifs: " + gestion.getEvenementsActifs().size()),
                new Label("Détails:"),
                createEventDetails()
        );

        root = new ScrollPane(content);
        root.setFitToWidth(true);
    }

    private VBox createEventDetails() {
        VBox details = new VBox(5);
        for (Evenement e : gestion.getTousLesEvenements()) {
            details.getChildren().add(new Label(
                    e.getId() + " - " + e.getNom() + " (" + (e.isAnnule() ? "Annulé" : "Actif") + ")"
            ));
        }
        return details;
    }

    public ScrollPane getRoot() {
        return root;
    }
}