package com.evenements.gui;

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
    public void start(Stage primaryStage) {
        // Initialize the GestionEvenements singleton (loads events from evenement.json)
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
    }

    @Override
    public void stop() {
        if (eventManagementView != null) {
            // Cleanup if needed (e.g., close resources)
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}