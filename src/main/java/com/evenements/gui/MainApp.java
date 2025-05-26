package com.evenements.gui;

import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import com.evenements.model.Organisateur;
import com.evenements.model.Participant;
import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.exception.ParticipantNonInscritException;
import com.evenements.service.GestionEvenements;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class MainApp extends Application {
    private GestionEvenements gestionEvenements;
    private ObservableList<Evenement> evenementList;
    private TextField idField;
    private TextField nomField;
    private TextField lieuField;
    private TextField capaciteField;
    private TextField artisteOrateurField;
    private TextField genreThemeField;
    private ComboBox<String> typeComboBox;
    private TextField participantNomField;
    private TextField participantEmailField;
    private Label statusLabel;
    private ListView<Evenement> evenementListView;

    @Override
    public void start(Stage primaryStage) {
        gestionEvenements = GestionEvenements.getInstance();

        // Initialisation des données de test
        try {
            Conference conf = new Conference("CONF001", "Tech Conf", LocalDateTime.now().plusDays(1),
                    "Paris", 100, "IA et Machine Learning");
            Concert concert = new Concert("CONC001", "Rock Night", LocalDateTime.now().plusDays(2),
                    "Lyon", 500, "The Rockers", "Rock");
            Organisateur org = new Organisateur("ORG001", "John Doe", "john@example.com");
            gestionEvenements.ajouterEvenement(conf);
            gestionEvenements.ajouterEvenement(concert);
            org.ajouterEvenementOrganise(conf);
            org.ajouterEvenementOrganise(concert);
            Participant p1 = new Participant("PART001", "Alice", "alice@example.com");
            conf.ajouterParticipant(p1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialisation de l'interface graphique
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Système de Gestion d'Événements");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Liste des événements
        evenementList = FXCollections.observableArrayList(gestionEvenements.getEvenements().values());
        evenementListView = new ListView<>(evenementList);
        evenementListView.setPrefHeight(200);
        evenementListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        statusLabel.setText(newSelection.afficherDetails());
                    }
                });

        // Formulaire
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        idField = new TextField();
        nomField = new TextField();
        lieuField = new TextField();
        capaciteField = new TextField();
        artisteOrateurField = new TextField();
        genreThemeField = new TextField();
        typeComboBox = new ComboBox<>(FXCollections.observableArrayList("Conference", "Concert"));
        typeComboBox.setValue("Conference");
        participantNomField = new TextField();
        participantEmailField = new TextField();
        statusLabel = new Label("Sélectionnez un événement pour voir les détails.");

        formGrid.add(new Label("ID:"), 0, 0);
        formGrid.add(idField, 1, 0);
        formGrid.add(new Label("Nom:"), 0, 1);
        formGrid.add(nomField, 1, 1);
        formGrid.add(new Label("Lieu:"), 0, 2);
        formGrid.add(lieuField, 1, 2);
        formGrid.add(new Label("Capacité:"), 0, 3);
        formGrid.add(capaciteField, 1, 3);
        formGrid.add(new Label("Date (YYYY-MM-DDTHH:MM):"), 0, 4);
        formGrid.add(new TextField("2025-05-27T10:00"), 1, 4);
        formGrid.add(new Label("Type:"), 0, 5);
        formGrid.add(typeComboBox, 1, 5);
        formGrid.add(new Label("Artiste/Thème:"), 0, 6);
        formGrid.add(artisteOrateurField, 1, 6);
        formGrid.add(new Label("Genre/Spécialité:"), 0, 7);
        formGrid.add(genreThemeField, 1, 7);
        formGrid.add(new Label("Nom Participant:"), 0, 8);
        formGrid.add(participantNomField, 1, 8);
        formGrid.add(new Label("Email Participant:"), 0, 9);
        formGrid.add(participantEmailField, 1, 9);

        // Boutons
        Button addEventButton = new Button("Ajouter Événement");
        addEventButton.setOnAction(e -> ajouterEvenement());

        Button deleteEventButton = new Button("Supprimer Événement");
        deleteEventButton.setOnAction(e -> supprimerEvenement());

        Button addParticipantButton = new Button("Ajouter Participant");
        addParticipantButton.setOnAction(e -> ajouterParticipant());

        Button saveButton = new Button("Sauvegarder Événements");
        saveButton.setOnAction(e -> {
            try {
                gestionEvenements.sauvegarderEvenements("evenements.json");
                statusLabel.setText("Événements sauvegardés avec succès!");
            } catch (Exception ex) {
                statusLabel.setText("Erreur lors de la sauvegarde: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(titleLabel, evenementListView, statusLabel, formGrid,
                addEventButton, deleteEventButton, addParticipantButton, saveButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Gestion d'Événements");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void ajouterEvenement() {
        try {
            Evenement event;
            if (typeComboBox.getValue().equals("Conference")) {
                event = new Conference(idField.getText(), nomField.getText(),
                        LocalDateTime.parse(nomField.getText()), lieuField.getText(),
                        Integer.parseInt(capaciteField.getText()), artisteOrateurField.getText());
            } else {
                event = new Concert(idField.getText(), nomField.getText(),
                        LocalDateTime.parse(nomField.getText()), lieuField.getText(),
                        Integer.parseInt(capaciteField.getText()), artisteOrateurField.getText(),
                        genreThemeField.getText());
            }
            gestionEvenements.ajouterEvenement(event);
            refreshEvenementList();
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(1000);
                    gestionEvenements.envoyerNotification("Nouvel événement ajouté: " + event.getNom());
                } catch (InterruptedException ex) {
                    statusLabel.setText("Erreur lors de la notification: " + ex.getMessage());
                }
            });
            statusLabel.setText("Événement ajouté avec succès!");
            clearFields();
        } catch (EvenementDejaExistantException | NumberFormatException | IllegalArgumentException ex) {
            statusLabel.setText("Erreur: " + ex.getMessage());
        }
    }

    private void supprimerEvenement() {
        Evenement selectedEvent = evenementListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            gestionEvenements.supprimerEvenement(selectedEvent.getId());
            refreshEvenementList();
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(1000);
                    gestionEvenements.envoyerNotification("Événement supprimé: " + selectedEvent.getNom());
                } catch (InterruptedException ex) {
                    statusLabel.setText("Erreur lors de la notification: " + ex.getMessage());
                }
            });
            statusLabel.setText("Événement supprimé avec succès!");
        } else {
            statusLabel.setText("Veuillez sélectionner un événement à supprimer.");
        }
    }

    private void ajouterParticipant() {
        Evenement selectedEvent = evenementListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                Participant participant = new Participant("PART" + System.currentTimeMillis(),
                        participantNomField.getText(), participantEmailField.getText());
                selectedEvent.ajouterParticipant(participant);
                refreshEvenementList();
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                        gestionEvenements.envoyerNotification("Participant ajouté à " + selectedEvent.getNom());
                    } catch (InterruptedException ex) {
                        statusLabel.setText("Erreur lors de la notification: " + ex.getMessage());
                    }
                });
                statusLabel.setText("Participant ajouté avec succès!");
                participantNomField.clear();
                participantEmailField.clear();
            } catch (CapaciteMaxAtteinteException ex) {
                statusLabel.setText("Erreur: " + ex.getMessage());
            }
        } else {
            statusLabel.setText("Veuillez sélectionner un événement.");
        }
    }

    private void refreshEvenementList() {
        evenementList.setAll(gestionEvenements.getEvenements().values());
    }

    private void clearFields() {
        idField.clear();
        nomField.clear();
        lieuField.clear();
        capaciteField.clear();
        artisteOrateurField.clear();
        genreThemeField.clear();
        participantNomField.clear();
        participantEmailField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}