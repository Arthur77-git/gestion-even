package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.model.Conference;
import com.evenements.model.Concert;
import com.evenements.singleton.GestionEvenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DashboardController {
    private GestionEvenements gestionEvenements;
    private ObservableList<XYChart.Series<String, Number>> eventsByMonthData;
    private ObservableList<PieChart.Data> eventTypeData;

    public DashboardController() {
        this.gestionEvenements = GestionEvenements.getInstance();
        this.eventsByMonthData = FXCollections.observableArrayList();
        this.eventTypeData = FXCollections.observableArrayList();
        refreshDashboard();
    }

    public int getTotalEvents() {
        return gestionEvenements.getNombreEvenements();
    }

    public int getActiveEvents() {
        return gestionEvenements.getEvenementsActifs().size();
    }

    public int getTotalParticipants() {
        return gestionEvenements.getTousLesEvenements().stream()
                .mapToInt(e -> e.getParticipants().size())
                .sum();
    }

    public ObservableList<XYChart.Series<String, Number>> getEventsByMonthData() {
        return eventsByMonthData;
    }

    public ObservableList<PieChart.Data> getEventTypeData() {
        return eventTypeData;
    }

    public void refreshDashboard() {
        // Refresh Events by Month Data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Événements");
        Map<String, Integer> eventsByMonth = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        for (Evenement event : gestionEvenements.getTousLesEvenements()) {
            String month = event.getDate().format(formatter);
            eventsByMonth.put(month, eventsByMonth.getOrDefault(month, 0) + 1);
        }

        eventsByMonth.forEach((month, count) -> series.getData().add(new XYChart.Data<>(month, count)));
        eventsByMonthData.setAll(series);

        // Refresh Event Type Data
        long conferences = gestionEvenements.getTousLesEvenements().stream()
                .filter(e -> e instanceof Conference)
                .count();
        long concerts = gestionEvenements.getTousLesEvenements().stream()
                .filter(e -> e instanceof Concert)
                .count();
        long others = gestionEvenements.getTousLesEvenements().stream()
                .filter(e -> !(e instanceof Conference) && !(e instanceof Concert))
                .count();

        eventTypeData.setAll(
                new PieChart.Data("Conférences", conferences),
                new PieChart.Data("Concerts", concerts),
                new PieChart.Data("Autres", others)
        );
    }
}