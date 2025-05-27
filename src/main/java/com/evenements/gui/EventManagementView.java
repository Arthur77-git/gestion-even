package com.evenements.gui;

import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.singleton.GestionEvenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A view class for managing events in a JavaFX application.
 * Displays a table of events, a toolbar for event actions (add, edit, delete, cancel),
 * and a details panel showing event information and participants.
 */
@SuppressWarnings("unchecked") // Suppress unchecked warnings for JavaFX internal raw types
public class EventManagementView {

    // UI components and data
    private BorderPane root;
    private TableView<EventTableItem> eventsTable;
    private ObservableList<EventTableItem> eventsList;
    private GestionEvenements gestion;

    // References to detail labels and participants list
    private Label nameDetail;
    private Label typeDetail;
    private Label dateDetail;
    private Label lieuDetail;
    private Label capaciteDetail;
    private Label participantsDetail;
    private ListView<String> participantsList;

    public EventManagementView() {
        gestion = GestionEvenements.getInstance();
        eventsList = FXCollections.observableArrayList();
        initializeUI();
        loadEvents();
    }

    // UI Setup Methods

    private void initializeUI() {
        root = new BorderPane();
        root.setPadding(new Insets(15));

        root.setTop(createToolbar());
        root.setCenter(createMainContent());
    }

    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("➕ Ajouter");
        addButton.setOnAction(e -> showAddEventDialog());

        Button editButton = new Button("✏️ Modifier");
        editButton.setOnAction(e -> editSelectedEvent());

        Button deleteButton = new Button("🗑️ Supprimer");
        deleteButton.setOnAction(e -> deleteSelectedEvent());

        Button cancelButton = new Button("❌ Annuler");
        cancelButton.setOnAction(e -> cancelSelectedEvent());

        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Rechercher...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            eventsList.clear();
            List<Evenement> events = gestion.rechercherParNom(newVal);
            for (Evenement e : events) {
                eventsList.add(new EventTableItem(e));
            }
        });

        ComboBox<String> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll("Tous", "Conférences", "Concerts", "Actifs", "Annulés");
        filterCombo.setValue("Tous");
        filterCombo.setOnAction(e -> filterEvents(filterCombo.getValue()));

        toolbar.getChildren().addAll(addButton, editButton, deleteButton, cancelButton, searchField, filterCombo);
        return toolbar;
    }

    private SplitPane createMainContent() {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(0.7);

        splitPane.getItems().addAll(createEventsTable(), createEventDetails());
        return splitPane;
    }

    private VBox createEventsTable() {
        eventsTable = new TableView<>();
        eventsTable.setItems(eventsList);

        TableColumn<EventTableItem, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<EventTableItem, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<EventTableItem, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<EventTableItem, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<EventTableItem, String> lieuCol = new TableColumn<>("Lieu");
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        TableColumn<EventTableItem, Integer> capaciteCol = new TableColumn<>("Capacité");
        capaciteCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        TableColumn<EventTableItem, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statut"));

        eventsTable.getColumns().addAll(idCol, nameCol, typeCol, dateCol, lieuCol, capaciteCol, statusCol);
        eventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> updateDetails(newSel));

        return new VBox(eventsTable);
    }

    private VBox createEventDetails() {
        VBox detailsPanel = new VBox(10);
        detailsPanel.setPadding(new Insets(15));

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);

        nameDetail = new Label("-");
        typeDetail = new Label("-");
        dateDetail = new Label("-");
        lieuDetail = new Label("-");
        capaciteDetail = new Label("-");
        participantsDetail = new Label("-");

        infoGrid.add(new Label("Nom:"), 0, 0); infoGrid.add(nameDetail, 1, 0);
        infoGrid.add(new Label("Type:"), 0, 1); infoGrid.add(typeDetail, 1, 1);
        infoGrid.add(new Label("Date:"), 0, 2); infoGrid.add(dateDetail, 1, 2);
        infoGrid.add(new Label("Lieu:"), 0, 3); infoGrid.add(lieuDetail, 1, 3);
        infoGrid.add(new Label("Capacité:"), 0, 4); infoGrid.add(capaciteDetail, 1, 4);
        infoGrid.add(new Label("Participants:"), 0, 5); infoGrid.add(participantsDetail, 1, 5);

        participantsList = new ListView<>();
        participantsList.setPrefHeight(200);

        detailsPanel.getChildren().addAll(infoGrid, participantsList);
        return detailsPanel;
    }

    // Event Handling Methods

    void loadEvents() {
        eventsList.clear();
        for (Evenement e : gestion.getTousLesEvenements()) {
            eventsList.add(new EventTableItem(e));
        }
    }

    private void filterEvents(String filter) {
        eventsList.clear();
        List<Evenement> events;
        switch (filter) {
            case "Conférences":
                events = gestion.getTousLesEvenements().stream()
                        .filter(e -> e instanceof Conference)
                        .collect(Collectors.toList());
                break;
            case "Concerts":
                events = gestion.getTousLesEvenements().stream()
                        .filter(e -> e instanceof Concert)
                        .collect(Collectors.toList());
                break;
            case "Actifs":
                events = gestion.getEvenementsActifs();
                break;
            case "Annulés":
                events = gestion.getTousLesEvenements().stream()
                        .filter(Evenement::isAnnule)
                        .collect(Collectors.toList());
                break;
            default:
                events = gestion.getTousLesEvenements();
        }
        for (Evenement e : events) {
            eventsList.add(new EventTableItem(e));
        }
    }

    private void showAddEventDialog() {
        new AddEventDialog(null).showDialog(event -> {
            try {
                gestion.ajouterEvenement(event);
                loadEvents();
                showAlert("Succès", "Événement ajouté avec succès");
                return null;
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage());
                return null;
            }
        });
    }

    private void editSelectedEvent() {
        EventTableItem selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Evenement event = gestion.rechercherEvenement(selected.getId()).orElse(null);
            if (event != null) {
                new AddEventDialog(event).showDialog(updatedEvent -> {
                    event.modifierEvenement(updatedEvent.getNom(), updatedEvent.getDate(), updatedEvent.getLieu());
                    loadEvents();
                    showAlert("Succès", "Événement modifié avec succès");
                    return null;
                });
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un événement");
        }
    }

    private void deleteSelectedEvent() {
        EventTableItem selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected != null && confirm("Supprimer l'événement ?")) {
            if (gestion.supprimerEvenement(selected.getId())) {
                loadEvents();
                showAlert("Succès", "Événement supprimé");
            } else {
                showAlert("Erreur", "Échec de la suppression");
            }
        }
    }

    private void cancelSelectedEvent() {
        EventTableItem selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected != null && confirm("Annuler l'événement ?")) {
            Evenement event = gestion.rechercherEvenement(selected.getId()).orElse(null);
            if (event != null) {
                event.annuler();
                loadEvents();
                showAlert("Succès", "Événement annulé");
            }
        }
    }

    private void updateDetails(EventTableItem item) {
        if (item != null) {
            Evenement event = gestion.rechercherEvenement(item.getId()).orElse(null);
            if (event != null) {
                nameDetail.setText(event.getNom());
                typeDetail.setText(item.getType());
                dateDetail.setText(event.getDate().toString());
                lieuDetail.setText(event.getLieu());
                capaciteDetail.setText(String.valueOf(event.getCapaciteMax()));
                participantsDetail.setText(String.valueOf(event.getParticipants().size()));
                participantsList.getItems().clear();
                for (Participant p : event.getParticipants()) {
                    participantsList.getItems().add(p.getNom());
                }
            }
        } else {
            nameDetail.setText("-");
            typeDetail.setText("-");
            dateDetail.setText("-");
            lieuDetail.setText("-");
            capaciteDetail.setText("-");
            participantsDetail.setText("-");
            participantsList.getItems().clear();
        }
    }

    // Utility Methods

    private void showAlert(String title, String message) {
        new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }

    private boolean confirm(String message) {
        return new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO)
                .showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    public BorderPane getRoot() {
        return root;
    }

    // Nested Class

    public static class EventTableItem {
        private final String id;
        private final String nom;
        private final String type;
        private final String date;
        private final String lieu;
        private final Integer capacite;
        private String statut;

        public EventTableItem(Evenement e) {
            this.id = e.getId();
            this.nom = e.getNom();
            this.type = e instanceof Conference ? "Conférence" : "Concert";
            this.date = e.getDate().toString();
            this.lieu = e.getLieu();
            this.capacite = e.getCapaciteMax();
            this.statut = e.isAnnule() ? "Annulé" : "Actif";
        }

        public String getId() { return id; }
        public String getNom() { return nom; }
        public String getType() { return type; }
        public String getDate() { return date; }
        public String getLieu() { return lieu; }
        public Integer getCapacite() { return capacite; }
        public String getStatut() { return statut; }
        public void setStatut(String statut) { this.statut = statut; }
    }
}