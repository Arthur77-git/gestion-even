package com.evenements.gui;

import com.evenements.factory.EvenementFactory;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class AddEventDialog {
    private Dialog<Evenement> dialog;
    private Evenement existingEvent;

    public AddEventDialog(Evenement existingEvent) {
        this.existingEvent = existingEvent;
        initialize();
    }

    private void initialize() {
        dialog = new Dialog<>();
        dialog.setTitle(existingEvent == null ? "Nouvel Événement" : "Modifier Événement");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField(existingEvent != null ? existingEvent.getId() : "");
        TextField nameField = new TextField(existingEvent != null ? existingEvent.getNom() : "");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Conférence", "Concert");
        typeCombo.setValue(existingEvent != null ? (existingEvent instanceof Conference ? "Conférence" : "Concert") : "Conférence");
        DatePicker datePicker = new DatePicker();
        TextField timeField = new TextField("10:00");
        TextField lieuField = new TextField(existingEvent != null ? existingEvent.getLieu() : "");
        TextField capaciteField = new TextField(existingEvent != null ? String.valueOf(existingEvent.getCapaciteMax()) : "100");
        TextField extraField = new TextField();

        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1); grid.add(nameField, 1, 1);
        grid.add(new Label("Type:"), 0, 2); grid.add(typeCombo, 1, 2);
        grid.add(new Label("Date:"), 0, 3); grid.add(datePicker, 1, 3);
        grid.add(new Label("Heure (HH:mm):"), 0, 4); grid.add(timeField, 1, 4);
        grid.add(new Label("Lieu:"), 0, 5); grid.add(lieuField, 1, 5);
        grid.add(new Label("Capacité:"), 0, 6); grid.add(capaciteField, 1, 6);
        grid.add(new Label("Thème/Artiste:"), 0, 7); grid.add(extraField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String[] timeParts = timeField.getText().split(":");
                    LocalDateTime dateTime = datePicker.getValue().atTime(
                            Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]));
                    return EvenementFactory.creerEvenement(
                            typeCombo.getValue().toLowerCase(),
                            idField.getText(),
                            nameField.getText(),
                            dateTime,
                            lieuField.getText(),
                            Integer.parseInt(capaciteField.getText()),
                            extraField.getText(),
                            typeCombo.getValue().equals("Concert") ? "Rock" : null
                    );
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erreur dans les données saisies").showAndWait();
                    return null;
                }
            }
            return null;
        });
    }

    public void showDialog(Consumer<Evenement> onSave) {
        dialog.showAndWait().ifPresent(onSave);
    }
}