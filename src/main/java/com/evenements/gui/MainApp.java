package com.evenements.gui;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.exception.ParticipantDejaInscritException;
import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.singleton.GestionEvenements;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class MainApp extends Application {

    private EventManagementView eventManagementView;
    private GestionEvenements gestion;

    @Override
    public void start(Stage primaryStage) throws CapaciteMaxAtteinteException, ParticipantDejaInscritException, EvenementDejaExistantException {
        // Initialize the GestionEvenements singleton
        gestion = GestionEvenements.getInstance();

        // Create the event management view
        eventManagementView = new EventManagementView();

        // Set up the scene
        Scene scene = new Scene(eventManagementView.getRoot(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/com/evenements/gui/styles.css").toExternalForm());

        // Configure the stage
        primaryStage.setTitle("Système de Gestion d'Événements");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Optional: Add sample data for testing
        initializeSampleData();
    }

    @Override
    public void stop() {
        if (eventManagementView != null) {
            // Cleanup if needed (e.g., close resources)
        }
    }

    private void initializeSampleData() throws EvenementDejaExistantException, CapaciteMaxAtteinteException, ParticipantDejaInscritException {
        // Add sample events
        Conference conference = new Conference("C001", "Tech Conference", LocalDateTime.now().plusDays(1), "Room A", 50, "AI");
        Concert concert = new Concert("C002", "Music Night", LocalDateTime.now().plusDays(2), "Hall B", 100, "Rock", "Pop");
        gestion.ajouterEvenement(conference);
        gestion.ajouterEvenement(concert);

        // Add sample participants
        Participant p1 = new Participant("P001", "John Doe", "john@example.com", null);
        Participant p2 = new Participant("P002", "Jane Doe", "jane@example.com", null);
        conference.ajouterParticipant(p1);
        concert.ajouterParticipant(p2);

        // Refresh the view
        eventManagementView.loadEvents();
    }

    public static void main(String[] args) {
        launch(args);
    }
}