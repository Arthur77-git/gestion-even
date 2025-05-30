package com.evenements.gui;

import com.evenements.model.Evenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class EventManagementView {
    private BorderPane root;
    private EventManagementController controller;
    private TableView<Evenement> eventsTable;

    public EventManagementView() {
        this.controller = new EventManagementController();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("event-management-container");
        root.setPadding(new Insets(20));

        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Gestion des Événements");
        title.getStyleClass().add("panel-title");

        HBox toolbar = createToolbar();
        eventsTable = createEventsTable();
        refreshTable(); // Ensure table is populated on startup

        mainContent.getChildren().addAll(title, toolbar, eventsTable);
        root.setCenter(mainContent);
    }

    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.getStyleClass().add("toolbar");
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Ajouter");
        addButton.getStyleClass().addAll("btn", "btn-primary");
        addButton.setOnAction(e -> showAddEventDialog());

        Button editButton = new Button("Modifier");
        editButton.getStyleClass().addAll("btn", "btn-warning");
        editButton.setOnAction(e -> {
            Evenement selected = eventsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showEditEventDialog(selected);
            } else {
                showAlert("Erreur", "Veuillez sélectionner un événement à modifier.");
            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.getStyleClass().addAll("btn", "btn-danger");
        deleteButton.setOnAction(e -> {
            Evenement selected = eventsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.deleteEvent(selected.getId());
                refreshTable();
            } else {
                showAlert("Erreur", "Veuillez sélectionner un événement à supprimer.");
            }
        });

        toolbar.getChildren().addAll(addButton, editButton, deleteButton);
        return toolbar;
    }

    private TableView<Evenement> createEventsTable() {
        TableView<Evenement> table = new TableView<>();
        table.getStyleClass().add("events-table");

        TableColumn<Evenement, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(100);

        TableColumn<Evenement, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nameCol.setPrefWidth(200);

        TableColumn<Evenement, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(150);

        TableColumn<Evenement, String> lieuCol = new TableColumn<>("Lieu");
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        lieuCol.setPrefWidth(150);

        TableColumn<Evenement, Integer> capaciteCol = new TableColumn<>("Capacité");
        capaciteCol.setCellValueFactory(new PropertyValueFactory<>("capaciteMax"));
        capaciteCol.setPrefWidth(100);

        TableColumn<Evenement, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().isAnnule() ? "Annulé" : "Actif"));
        statusCol.setPrefWidth(100);

        table.getColumns().addAll(idCol, nameCol, dateCol, lieuCol, capaciteCol, statusCol);
        return table;
    }

    private void showAddEventDialog() {
        AddEventDialog dialog = new AddEventDialog();
        dialog.showAndWait().ifPresent(event -> {
            try {
                controller.addEvent(event);
                refreshTable();
            } catch (Exception e) {
                showAlert("Erreur", "Impossible d'ajouter l'événement : " + e.getMessage());
            }
        });
    }

    private void showEditEventDialog(Evenement event) {
        AddEventDialog dialog = new AddEventDialog(event);
        dialog.showAndWait().ifPresent(updatedEvent -> {
            try {
                controller.updateEvent(updatedEvent);
                refreshTable();
            } catch (Exception e) {
                showAlert("Erreur", "Impossible de modifier l'événement : " + e.getMessage());
            }
        });
    }

    private void refreshTable() {
        eventsTable.setItems(controller.getAllEvents());
        eventsTable.refresh();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }
}