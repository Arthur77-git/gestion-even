package com.evenements.gui;

import com.evenements.model.Concert;
import com.evenements.model.Evenement;
import com.evenements.singleton.GestionEvenements;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.util.List;

public class DashboardView {
    private BorderPane root;
    private GestionEvenements gestion;

    public DashboardView() {
        gestion = GestionEvenements.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.setPadding(new Insets(20));

        VBox mainContent = new VBox(20);
        mainContent.getChildren().addAll(createStatsCards(), createPieChartSection());
        root.setCenter(mainContent);
    }

    private HBox createStatsCards() {
        HBox statsContainer = new HBox(20);
        statsContainer.setAlignment(Pos.CENTER);

        statsContainer.getChildren().addAll(
                createStatsCard("Événements Totaux", String.valueOf(gestion.getNombreEvenements()), "📅"),
                createStatsCard("Événements Actifs", String.valueOf(gestion.getEvenementsActifs().size()), "🎯"),
                createStatsCard("Participants", String.valueOf(countParticipants()), "👥"),
                createStatsCard("À venir", String.valueOf(countUpcomingEvents()), "⏰")
        );
        return statsContainer;
    }

    private VBox createStatsCard(String title, String value, String icon) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.getStyleClass().add("stats-card");

        card.getChildren().addAll(new Label(icon), new Label(value), new Label(title));
        return card;
    }

    private VBox createPieChartSection() {
        VBox pieChartContainer = new VBox(10);

        PieChart pieChart = new PieChart();
        pieChart.setPrefSize(400, 300);

        long conferences = gestion.getTousLesEvenements().stream().filter(e -> e instanceof Concert).count();
        long concerts = gestion.getTousLesEvenements().size() - conferences;

        pieChart.getData().addAll(
                new PieChart.Data("Conférences", conferences),
                new PieChart.Data("Concerts", concerts)
        );

        pieChartContainer.getChildren().addAll(new Label("Types d'Événements"), pieChart);
        return pieChartContainer;
    }

    private int countParticipants() {
        return gestion.getTousLesEvenements().stream()
                .mapToInt(e -> e.getParticipants().size())
                .sum();
    }

    private long countUpcomingEvents() {
        return gestion.getEvenementsActifs().stream()
                .filter(e -> e.getDate().isAfter(LocalDateTime.now()))
                .count();
    }

    public BorderPane getRoot() {
        return root;
    }
}