package com.evenements.gui;

import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.evenements.model.Evenement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddEventDialog extends Dialog<Evenement> {
    private TextField idField;
    private TextField nameField;
    private DatePicker dateField;
    private ComboBox<String> hourCombo;
    private ComboBox<String> minuteCombo;
    private TextField lieuField;
    private TextField capaciteField;
    private ComboBox<String> typeCombo;
    private TextField themeField;
    private TextField intervenantsField;
    private TextField artisteField;
    private TextField genreField;

    public AddEventDialog() {
        this(null);
    }

    public AddEventDialog(Evenement event) {
        setTitle(event == null ? "Ajouter un Événement" : "Modifier un Événement");
        setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = createFormGrid();
        initializeFields(grid, event);

        getDialogPane().setContent(grid);

        // Enable/Disable Save button based on input
        getDialogPane().lookupButton(saveButtonType).setDisable(true);
        idField.textProperty().addListener((obs, old, newValue) -> validateInput());
        nameField.textProperty().addListener((obs, old, newValue) -> validateInput());
        dateField.valueProperty().addListener((obs, old, newValue) -> validateInput());
        lieuField.textProperty().addListener((obs, old, newValue) -> validateInput());
        capaciteField.textProperty().addListener((obs, old, newValue) -> validateInput());
        typeCombo.valueProperty().addListener((obs, old, newValue) -> validateInput());
        themeField.textProperty().addListener((obs, old, newValue) -> validateInput());
        intervenantsField.textProperty().addListener((obs, old, newValue) -> validateInput());
        artisteField.textProperty().addListener((obs, old, newValue) -> validateInput());
        genreField.textProperty().addListener((obs, old, newValue) -> validateInput());

        // Handle type change to show/hide specific fields
        typeCombo.valueProperty().addListener((obs, old, newValue) -> updateFieldVisibility());

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return createEvent();
            }
            return null;
        });

        // Apply styles
        getDialogPane().getStyleClass().add("dialog-container");
        getDialogPane().getStylesheets().add(getClass().getResource("/styles/main-styles.css").toExternalForm());
        updateFieldVisibility();

        // Set dialog size
        getDialogPane().setPrefWidth(600);
        getDialogPane().setPrefHeight(700);
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(700);
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(30));

        Label idLabel = new Label("ID:");
        idField = new TextField();
        idField.getStyleClass().add("form-field");
        idField.setPrefWidth(300);

        Label nameLabel = new Label("Nom:");
        nameField = new TextField();
        nameField.getStyleClass().add("form-field");
        nameField.setPrefWidth(300);

        Label dateLabel = new Label("Date:");
        dateField = new DatePicker();
        dateField.getStyleClass().add("form-field");
        dateField.setPrefWidth(300);

        Label timeLabel = new Label("Heure:");
        hourCombo = new ComboBox<>();
        for (int i = 0; i < 24; i++) {
            hourCombo.getItems().add(String.format("%02d", i));
        }
        hourCombo.setValue("09");
        hourCombo.getStyleClass().add("time-combo");
        hourCombo.setPrefWidth(100);

        minuteCombo = new ComboBox<>();
        for (int i = 0; i < 60; i += 5) {
            minuteCombo.getItems().add(String.format("%02d", i));
        }
        minuteCombo.setValue("00");
        minuteCombo.getStyleClass().add("time-combo");
        minuteCombo.setPrefWidth(100);

        Label lieuLabel = new Label("Lieu:");
        lieuField = new TextField();
        lieuField.getStyleClass().add("form-field");
        lieuField.setPrefWidth(300);

        Label capaciteLabel = new Label("Capacité:");
        capaciteField = new TextField();
        capaciteField.getStyleClass().add("form-field");
        capaciteField.setPrefWidth(300);

        Label typeLabel = new Label("Type:");
        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Conférence", "Concert", "Générique");
        typeCombo.setValue("Générique");
        typeCombo.getStyleClass().add("form-combo");
        typeCombo.setPrefWidth(300);

        Label themeLabel = new Label("Thème (Conférence):");
        themeField = new TextField();
        themeField.getStyleClass().add("form-field");
        themeField.setPrefWidth(300);

        Label intervenantsLabel = new Label("Intervenants (Conférence):");
        intervenantsField = new TextField();
        intervenantsField.getStyleClass().add("form-field");
        intervenantsField.setPromptText("Séparés par des virgules");
        intervenantsField.setPrefWidth(300);

        Label artisteLabel = new Label("Artiste (Concert):");
        artisteField = new TextField();
        artisteField.getStyleClass().add("form-field");
        artisteField.setPrefWidth(300);

        Label genreLabel = new Label("Genre (Concert):");
        genreField = new TextField();
        genreField.getStyleClass().add("form-field");
        genreField.setPrefWidth(300);

        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(dateLabel, 0, 2);
        grid.add(dateField, 1, 2);
        grid.add(timeLabel, 0, 3);
        HBox timeBox = new HBox(10);
        timeBox.getChildren().addAll(hourCombo, new Label(":"), minuteCombo);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        grid.add(timeBox, 1, 3);
        grid.add(lieuLabel, 0, 4);
        grid.add(lieuField, 1, 4);
        grid.add(capaciteLabel, 0, 5);
        grid.add(capaciteField, 1, 5);
        grid.add(typeLabel, 0, 6);
        grid.add(typeCombo, 1, 6);
        grid.add(themeLabel, 0, 7);
        grid.add(themeField, 1, 7);
        grid.add(intervenantsLabel, 0, 8);
        grid.add(intervenantsField, 1, 8);
        grid.add(artisteLabel, 0, 9);
        grid.add(artisteField, 1, 9);
        grid.add(genreLabel, 0, 10);
        grid.add(genreField, 1, 10);

        return grid;
    }

    private void initializeFields(GridPane grid, Evenement event) {
        if (event != null) {
            idField.setText(event.getId());
            idField.setDisable(true); // Prevent editing ID
            nameField.setText(event.getNom());
            dateField.setValue(event.getDate().toLocalDate());
            hourCombo.setValue(String.format("%02d", event.getDate().getHour()));
            minuteCombo.setValue(String.format("%02d", event.getDate().getMinute()));
            lieuField.setText(event.getLieu());
            capaciteField.setText(String.valueOf(event.getCapaciteMax()));

            if (event instanceof Conference) {
                typeCombo.setValue("Conférence");
                Conference conf = (Conference) event;
                themeField.setText(conf.getTheme());
                intervenantsField.setText(String.join(",", conf.getIntervenants()));
            } else if (event instanceof Concert) {
                typeCombo.setValue("Concert");
                Concert concert = (Concert) event;
                artisteField.setText(concert.getArtiste());
                genreField.setText(concert.getGenreMusical());
            } else {
                typeCombo.setValue("Générique");
            }
        }
    }

    private void updateFieldVisibility() {
        boolean isConference = typeCombo.getValue().equals("Conférence");
        boolean isConcert = typeCombo.getValue().equals("Concert");

        themeField.setVisible(isConference);
        themeField.setManaged(isConference);
        intervenantsField.setVisible(isConference);
        intervenantsField.setManaged(isConference);
        artisteField.setVisible(isConcert);
        artisteField.setManaged(isConcert);
        genreField.setVisible(isConcert);
        genreField.setManaged(isConcert);

        validateInput();
    }

    private void validateInput() {
        boolean valid = !idField.getText().trim().isEmpty() &&
                !nameField.getText().trim().isEmpty() &&
                dateField.getValue() != null &&
                !lieuField.getText().trim().isEmpty() &&
                capaciteField.getText().matches("\\d+");

        if (typeCombo.getValue().equals("Conférence")) {
            valid = valid && !themeField.getText().trim().isEmpty();
            // Intervenants field is optional
        } else if (typeCombo.getValue().equals("Concert")) {
            valid = valid && !artisteField.getText().trim().isEmpty();
            // Genre field is optional
        }

        getDialogPane().lookupButton(getDialogPane().getButtonTypes().get(0)).setDisable(!valid);
    }

    private Evenement createEvent() {
        String id = idField.getText();
        String nom = nameField.getText();
        LocalDateTime date = LocalDateTime.of(
                dateField.getValue(),
                java.time.LocalTime.of(
                        Integer.parseInt(hourCombo.getValue()),
                        Integer.parseInt(minuteCombo.getValue())
                )
        );
        String lieu = lieuField.getText();
        int capacite = Integer.parseInt(capaciteField.getText());

        String type = typeCombo.getValue();
        if (type.equals("Conférence")) {
            String theme = themeField.getText();
            List<String> intervenants = intervenantsField.getText().trim().isEmpty()
                    ? List.of()
                    : Arrays.stream(intervenantsField.getText().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            return new Conference(id, nom, date, lieu, capacite, theme, intervenants);
        } else if (type.equals("Concert")) {
            String artiste = artisteField.getText();
            String genre = genreField.getText().trim().isEmpty() ? "" : genreField.getText();
            return new Concert(id, nom, date, lieu, capacite, artiste, genre);
        } else {
            return new Evenement(id, nom, date, lieu, capacite) {
                @Override
                public void afficherDetails() {
                    System.out.println("Événement: " + getNom());
                }
            };
        }
    }
}