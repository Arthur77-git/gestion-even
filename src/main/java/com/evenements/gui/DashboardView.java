package com.evenements.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDateTime;

public class DashboardView {
    private BorderPane root;
    private DashboardController controller;

    public DashboardView() {
        this.controller = new DashboardController();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("dashboard-container");
        root.setPadding(new Insets(20));

        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Tableau de Bord");
        title.getStyleClass().add("dashboard-title");

        Button refreshButton = new Button("Actualiser");
        refreshButton.getStyleClass().addAll("btn", "btn-primary");
        refreshButton.setOnAction(e -> controller.refreshDashboard());

        GridPane metricsGrid = createMetricsGrid();
        HBox chartsBox = createChartsBox();

        mainContent.getChildren().addAll(title, refreshButton, metricsGrid, chartsBox);
        root.setCenter(mainContent);
    }

    private GridPane createMetricsGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("metrics-grid");
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        // Total Events Card
        VBox totalEventsCard = createMetricCard("Événements Totaux", String.valueOf(controller.getTotalEvents()));
        grid.add(totalEventsCard, 0, 0);

        // Active Events Card
        VBox activeEventsCard = createMetricCard("Événements Actifs", String.valueOf(controller.getActiveEvents()));
        grid.add(activeEventsCard, 1, 0);

        // Total Participants Card
        VBox participantsCard = createMetricCard("Participants", String.valueOf(controller.getTotalParticipants()));
        grid.add(participantsCard, 2, 0);

        return grid;
    }

    private VBox createMetricCard(String title, String value) {
        VBox card = new VBox(10);
        card.getStyleClass().add("metric-card");
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("metric-title");

        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("metric-value");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private HBox createChartsBox() {
        HBox chartsBox = new HBox(20);
        chartsBox.setAlignment(Pos.CENTER);

        // Bar Chart for Events by Month
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Mois");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre d'Événements");
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Événements par Mois");
        barChart.getStyleClass().add("chart");
        barChart.setData(controller.getEventsByMonthData());
        barChart.setPrefWidth(600);

        // Pie Chart for Event Types
        PieChart pieChart = new PieChart(controller.getEventTypeData());
        pieChart.setTitle("Répartition des Types d'Événements");
        pieChart.getStyleClass().add("chart");
        pieChart.setPrefWidth(400);

        chartsBox.getChildren().addAll(barChart, pieChart);
        return chartsBox;
    }

    public BorderPane getRoot() {
        return root;
    }
}