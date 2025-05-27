package com.evenements.gui;

import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddEventDialog {
    private Dialog<Evenement> dialog;
    private TextField idField;
    private TextField nameField;
    private TextField dateField;
    private TextField lieuField;
    private TextField capaciteField;
    private TextField themeField; // For Conference
    private TextField artisteField; // For Concert
    private TextField genreField; // For Concert
    private ComboBox<String> typeCombo;

    public AddEventDialog(Evenement event) {
        dialog = new Dialog<>();
        dialog.setTitle(event == null ? "Ajouter un événement" : "Modifier un événement");
        dialog.setHeaderText(event == null ? "Créer un nouvel événement" : "Modifier un événement existant");

        // Set up the dialog buttons
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        idField = new TextField(event != null ? event.getId() : "");
        idField.setPromptText("ID");
        idField.setDisable(event != null); // ID cannot be edited

        nameField = new TextField(event != null ? event.getNom() : "");
        nameField.setPromptText("Nom");

        dateField = new TextField(event != null ? event.getDate().toString() : "");
        dateField.setPromptText("Date (yyyy-MM-dd HH:mm)");

        lieuField = new TextField(event != null ? event.getLieu() : "");
        lieuField.setPromptText("Lieu");

        capaciteField = new TextField(event != null ? String.valueOf(event.getCapaciteMax()) : "");
        capaciteField.setPromptText("Capacité");

        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Conférence", "Concert");
        typeCombo.setValue(event instanceof Conference ? "Conférence" : event instanceof Concert ? "Concert" : "Conférence");

        themeField = new TextField(event instanceof Conference ? ((Conference) event).getTheme() : "");
        themeField.setPromptText("Thème (Conférence)");
        themeField.setDisable(typeCombo.getValue().equals("Concert"));

        artisteField = new TextField(event instanceof Concert ? ((Concert) event).getArtiste() : "");
        artisteField.setPromptText("Artiste (Concert)");
        artisteField.setDisable(typeCombo.getValue().equals("Conférence"));

        genreField = new TextField(event instanceof Concert ? ((Concert) event).getGenre() : "");
        genreField.setPromptText("Genre (Concert)");
        genreField.setDisable(typeCombo.getValue().equals("Conférence"));

        // Enable/disable fields based on event type
        typeCombo.setOnAction(e -> {
            themeField.setDisable(typeCombo.getValue().equals("Concert"));
            artisteField.setDisable(typeCombo.getValue().equals("Conférence"));
            genreField.setDisable(typeCombo.getValue().equals("Conférence"));
        });

        grid.add(new Label("Type:"), 0, 0);
        grid.add(typeCombo, 1, 0);
        grid.add(new Label("ID:"), 0, 1);
        grid.add(idField, 1, 1);
        grid.add(new Label("Nom:"), 0, 2);
        grid.add(nameField, 1, 2);
        grid.add(new Label("Date:"), 0, 3);
        grid.add(dateField, 1, 3);
        grid.add(new Label("Lieu:"), 0, 4);
        grid.add(lieuField, 1, 4);
        grid.add(new Label("Capacité:"), 0, 5);
        grid.add(capaciteField, 1, 5);
        grid.add(new Label("Thème:"), 0, 6);
        grid.add(themeField, 1, 6);
        grid.add(new Label("Artiste:"), 0, 7);
        grid.add(artisteField, 1, 7);
        grid.add(new Label("Genre:"), 0, 8);
        grid.add(genreField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        // Validate input and create the event
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String id = validateField(idField.getText(), "ID");
                    String name = validateField(nameField.getText(), "Nom");
                    String lieu = validateField(lieuField.getText(), "Lieu");

                    LocalDateTime date = validateDate(dateField.getText());
                    int capacite = validateCapacity(capaciteField.getText());

                    if (typeCombo.getValue().equals("Conférence")) {
                        String theme = validateField(themeField.getText(), "Thème");
                        return new Conference(id, name, date, lieu, capacite, theme);
                    } else {
                        String artiste = validateField(artisteField.getText(), "Artiste");
                        String genre = validateField(genreField.getText(), "Genre");
                        return new Concert(id, name, date, lieu, capacite, artiste, genre);
                    }
                } catch (IllegalArgumentException e) {
                    showAlert("Erreur de saisie", e.getMessage());
                    return null;
                }
            }
            return null;
        });
    }

    private String validateField(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " ne peut pas être vide.");
        }
        return value.trim();
    }

    private LocalDateTime validateDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide. Utilisez yyyy-MM-dd HH:mm (ex. 2025-05-27 14:30).");
        }
    }

    private int validateCapacity(String capaciteStr) {
        try {
            int capacite = Integer.parseInt(capaciteStr);
            if (capacite <= 0) {
                throw new IllegalArgumentException("La capacité doit être un nombre positif.");
            }
            return capacite;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La capacité doit être un nombre entier valide.");
        }
    }

    public void showDialog(Callback<Evenement, Void> callback) {
        dialog.showAndWait().ifPresent(event -> {
            if (event != null) {
                callback.call(event);
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}