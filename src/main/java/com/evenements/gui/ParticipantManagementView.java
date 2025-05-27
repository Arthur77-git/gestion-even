package com.evenements.gui;

import com.evenements.factory.EvenementFactory;
import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.service.EmailNotificationService;
import com.evenements.singleton.GestionEvenements;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ParticipantManagementView {
    private BorderPane root;
    private GestionEvenements gestion;

    public ParticipantManagementView() {
        gestion = GestionEvenements.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.setPadding(new Insets(15));

        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));

        ComboBox<String> eventCombo = new ComboBox<>();
        gestion.getTousLesEvenements().forEach(e -> eventCombo.getItems().add(e.getId() + " - " + e.getNom()));
        eventCombo.setPromptText("Sélectionner un événement");

        TextField nameField = new TextField();
        nameField.setPromptText("Nom du participant");
        TextField emailField = new TextField();
        emailField.setPromptText("Email du participant");

        Button addButton = new Button("➕ Ajouter Participant");
        addButton.setOnAction(e -> {
            if (eventCombo.getValue() != null && !nameField.getText().isEmpty() && !emailField.getText().isEmpty()) {
                String eventId = eventCombo.getValue().split(" - ")[0];
                Evenement event = gestion.rechercherEvenement(eventId).orElse(null);
                if (event != null) {
                    Participant participant = EvenementFactory.creerParticipant(
                            "P" + System.currentTimeMillis(),
                            nameField.getText(),
                            emailField.getText(),
                            new EmailNotificationService()
                    );
                    try {
                        event.ajouterParticipant(participant);
                        showAlert("Succès", "Participant ajouté");
                    } catch (Exception ex) {
                        showAlert("Erreur", ex.getMessage());
                    }
                }
            } else {
                showAlert("Erreur", "Veuillez remplir tous les champs");
            }
        });

        toolbar.getChildren().addAll(eventCombo, nameField, emailField, addButton);
        root.setTop(toolbar);

        ListView<String> participantsList = new ListView<>();
        eventCombo.setOnAction(e -> {
            participantsList.getItems().clear();
            if (eventCombo.getValue() != null) {
                String eventId = eventCombo.getValue().split(" - ")[0];
                Evenement event = gestion.rechercherEvenement(eventId).orElse(null);
                if (event != null) {
                    event.getParticipants().forEach(p -> participantsList.getItems().add(p.getNom()));
                }
            }
        });

        root.setCenter(participantsList);
    }

    private void showAlert(String title, String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }
}