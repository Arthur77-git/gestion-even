package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.Optional;

public class ParticipantManagementView {
    private BorderPane root;
    private ParticipantManagementController controller;
    private TableView<Participant> participantsTable;
    private ComboBox<Evenement> eventComboBox;

    public ParticipantManagementView() {
        this.controller = new ParticipantManagementController();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("participant-management-container");
        root.setPadding(new Insets(20));

        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Gestion des Participants");
        title.getStyleClass().add("panel-title");

        HBox eventSelector = createEventSelector();
        participantsTable = createParticipantsTable();
        HBox actionButtons = createActionButtons();

        mainContent.getChildren().addAll(title, eventSelector, participantsTable, actionButtons);
        root.setCenter(mainContent);
    }

    private HBox createEventSelector() {
        HBox selectorBox = new HBox(10);
        selectorBox.getStyleClass().add("selector-box");
        selectorBox.setAlignment(Pos.CENTER_LEFT);

        Label eventLabel = new Label("Événement:");
        eventLabel.getStyleClass().add("filter-label");
        eventComboBox = new ComboBox<>();
        eventComboBox.setItems(controller.getAllEvents());
        eventComboBox.getStyleClass().add("event-combo");
        eventComboBox.setPrefWidth(300);

        eventComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateParticipantsTable(newVal.getId());
            }
        });

        selectorBox.getChildren().addAll(eventLabel, eventComboBox);
        return selectorBox;
    }

    private TableView<Participant> createParticipantsTable() {
        TableView<Participant> table = new TableView<>();
        table.getStyleClass().add("participants-table");

        TableColumn<Participant, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(100);

        TableColumn<Participant, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomCol.setPrefWidth(200);

        TableColumn<Participant, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);

        table.getColumns().addAll(idCol, nomCol, emailCol);
        return table;
    }

    private HBox createActionButtons() {
        HBox actionBox = new HBox(10);
        actionBox.getStyleClass().add("action-box");
        actionBox.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Ajouter Participant");
        addButton.getStyleClass().addAll("btn", "btn-primary");
        addButton.setOnAction(e -> showAddParticipantDialog());

        Button removeButton = new Button("Supprimer Participant");
        removeButton.getStyleClass().addAll("btn", "btn-danger");
        removeButton.setOnAction(e -> removeSelectedParticipant());

        actionBox.getChildren().addAll(addButton, removeButton);
        return actionBox;
    }

    private void updateParticipantsTable(String eventId) {
        participantsTable.setItems(controller.getParticipantsForEvent(eventId));
        participantsTable.refresh();
    }

    private void showAddParticipantDialog() {
        Evenement selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Erreur", "Veuillez sélectionner un événement.");
            return;
        }

        Dialog<Participant> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un Participant");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        idField.getStyleClass().add("form-field");

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.getStyleClass().add("form-field");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("form-field");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable Save button
        dialog.getDialogPane().lookupButton(saveButtonType).setDisable(true);
        idField.textProperty().addListener((obs, old, newVal) ->
                dialog.getDialogPane().lookupButton(saveButtonType).setDisable(
                        newVal.trim().isEmpty() || nomField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()
                ));
        nomField.textProperty().addListener((obs, old, newVal) ->
                dialog.getDialogPane().lookupButton(saveButtonType).setDisable(
                        newVal.trim().isEmpty() || idField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()
                ));
        emailField.textProperty().addListener((obs, old, newVal) ->
                dialog.getDialogPane().lookupButton(saveButtonType).setDisable(
                        newVal.trim().isEmpty() || idField.getText().trim().isEmpty() || nomField.getText().trim().isEmpty()
                ));

        dialog.getDialogPane().getStyleClass().add("dialog-container");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles/main-styles.css").toExternalForm());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Participant(idField.getText(), nomField.getText(), emailField.getText());
            }
            return null;
        });

        Optional<Participant> result = dialog.showAndWait();
        result.ifPresent(participant -> {
            controller.addParticipantToEvent(selectedEvent.getId(), participant);
            updateParticipantsTable(selectedEvent.getId());
        });
    }

    private void removeSelectedParticipant() {
        Evenement selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
        Participant selectedParticipant = participantsTable.getSelectionModel().getSelectedItem();

        if (selectedEvent == null || selectedParticipant == null) {
            showAlert("Erreur", "Veuillez sélectionner un événement et un participant.");
            return;
        }

        controller.removeParticipantFromEvent(selectedEvent.getId(), selectedParticipant);
        updateParticipantsTable(selectedEvent.getId());
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