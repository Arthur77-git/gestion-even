package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.service.EmailNotificationService;
import com.evenements.singleton.GestionEvenements;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class NotificationView {
    private BorderPane root;
    private GestionEvenements gestion;

    public NotificationView() {
        gestion = GestionEvenements.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.setPadding(new Insets(15));

        HBox form = new HBox(10);
        form.setPadding(new Insets(10));

        ComboBox<String> eventCombo = new ComboBox<>();
        gestion.getTousLesEvenements().forEach(e -> eventCombo.getItems().add(e.getId() + " - " + e.getNom()));
        eventCombo.setPromptText("Sélectionner un événement");

        TextArea messageField = new TextArea();
        messageField.setPromptText("Entrez votre message...");
        messageField.setPrefRowCount(3);

        Button sendButton = new Button("📤 Envoyer");
        sendButton.setOnAction(e -> {
            if (eventCombo.getValue() != null && !messageField.getText().isEmpty()) {
                String eventId = eventCombo.getValue().split(" - ")[0];
                Evenement event = gestion.rechercherEvenement(eventId).orElse(null);
                if (event != null) {
                    EmailNotificationService service = new EmailNotificationService();
                    event.getParticipants().forEach(p ->
                            service.envoyerNotificationAsync("Notification pour " + p.getNom() + " (" + p.getEmail() + "): " + messageField.getText()));
                    showAlert("Succès", "Notifications envoyées");
                }
            } else {
                showAlert("Erreur", "Veuillez sélectionner un événement et entrer un message");
            }
        });

        form.getChildren().addAll(eventCombo, messageField, sendButton);
        root.setTop(form);

        ListView<String> logList = new ListView<>();
        logList.getItems().add("Notifications envoyées...");
        root.setCenter(logList);
    }

    private void showAlert(String title, String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }
}