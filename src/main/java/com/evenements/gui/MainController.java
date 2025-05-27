package com.evenements.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    @FXML private TabPane tabPane;
    @FXML private Label statusLabel;
    @FXML private Label timeLabel;

    private Timeline clockTimeline;

    @FXML
    private void initialize() {
        // Setup tabs
        tabPane.getTabs().addAll(
                new Tab("📊 Tableau de Bord", new DashboardView().getRoot()),
                new Tab("🎯 Événements", new EventManagementView().getRoot()),
                new Tab("👥 Participants", new ParticipantManagementView().getRoot()),
                new Tab("🔔 Notifications", new NotificationView().getRoot()),
                new Tab("📈 Rapports", new ReportView().getRoot())
        );

        // Setup clock
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
                timeLabel.setText(LocalDateTime.now().format(formatter))));
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
        timeLabel.setText(LocalDateTime.now().format(formatter));
    }

    public void cleanup() {
        if (clockTimeline != null) {
            clockTimeline.stop();
        }
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
}