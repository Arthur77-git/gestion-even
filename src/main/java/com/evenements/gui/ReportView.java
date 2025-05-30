package com.evenements.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class ReportView {
    private BorderPane root;
    private ReportController controller;
    private TableView<ReportItem> reportTable;

    public ReportView() {
        this.controller = new ReportController();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("report-container");
        root.setPadding(new Insets(20));

        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Rapports");
        title.getStyleClass().add("report-title");

        HBox filterBox = createFilterBox();
        reportTable = createReportTable();
        Button exportButton = createExportButton();

        mainContent.getChildren().addAll(title, filterBox, reportTable, exportButton);
        root.setCenter(mainContent);

        // Initialize table data after reportTable is created
        updateReportTable("Événements", "Tous");
    }

    private HBox createFilterBox() {
        HBox filterBox = new HBox(15);
        filterBox.getStyleClass().add("filter-box");
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(10));

        Label typeLabel = new Label("Type de Rapport:");
        typeLabel.getStyleClass().add("filter-label");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Événements", "Participants");
        typeCombo.setValue("Événements");
        typeCombo.getStyleClass().add("filter-combo");

        Label statusLabel = new Label("Statut:");
        statusLabel.getStyleClass().add("filter-label");
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Tous", "Actifs", "Annulés");
        statusCombo.setValue("Tous");
        statusCombo.getStyleClass().add("filter-combo");

        Button generateButton = new Button("Générer");
        generateButton.getStyleClass().addAll("btn", "btn-primary");
        generateButton.setOnAction(e -> updateReportTable(typeCombo.getValue(), statusCombo.getValue()));

        filterBox.getChildren().addAll(typeLabel, typeCombo, statusLabel, statusCombo, generateButton);
        return filterBox;
    }

    private TableView<ReportItem> createReportTable() {
        TableView<ReportItem> table = new TableView<>();
        table.getStyleClass().add("report-table");

        TableColumn<ReportItem, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(100);

        TableColumn<ReportItem, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<ReportItem, String> detailCol = new TableColumn<>("Détail");
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detail"));
        detailCol.setPrefWidth(300);

        TableColumn<ReportItem, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        table.getColumns().addAll(idCol, nameCol, detailCol, statusCol);
        return table;
    }

    private Button createExportButton() {
        Button exportButton = new Button("Exporter en CSV");
        exportButton.getStyleClass().addAll("btn", "btn-secondary");
        exportButton.setOnAction(e -> controller.exportToCSV(reportTable.getItems()));
        return exportButton;
    }

    private void updateReportTable(String type, String status) {
        reportTable.getItems().clear();
        reportTable.setItems(controller.generateReport(type, status));
    }

    public BorderPane getRoot() {
        return root;
    }

    public static class ReportItem {
        private String id;
        private String name;
        private String detail;
        private String status;

        public ReportItem(String id, String name, String detail, String status) {
            this.id = id;
            this.name = name;
            this.detail = detail;
            this.status = status;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getDetail() { return detail; }
        public String getStatus() { return status; }
    }
}