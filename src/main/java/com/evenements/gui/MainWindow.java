package com.evenements.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainWindow {
    private static MainWindow instance;
    private BorderPane root;
    private MainController controller;
    private NotificationView notificationView;

    private MainWindow() {
        this.controller = new MainController();
        initializeUI();
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("main-container");

        VBox header = createHeader();
        root.setTop(header);

        TabPane tabPane = createTabPane();
        root.setCenter(tabPane);

        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Système de Gestion d'Événements");
        titleLabel.getStyleClass().add("title-label");

        Label subtitleLabel = new Label("Gérez vos conférences, concerts et événements en toute simplicité");
        subtitleLabel.getStyleClass().add("subtitle-label");

        header.getChildren().addAll(titleLabel, subtitleLabel);
        return header;
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("main-tabpane");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab dashboardTab = new Tab("📊 Tableau de Bord");
        DashboardView dashboardView = new DashboardView();
        dashboardTab.setContent(dashboardView.getRoot());

        Tab eventsTab = new Tab("🎯 Gestion des Événements");
        EventManagementView eventView = new EventManagementView();
        eventsTab.setContent(eventView.getRoot());

        Tab participantsTab = new Tab("👥 Participants");
        ParticipantManagementView participantView = new ParticipantManagementView();
        participantsTab.setContent(participantView.getRoot());

        Tab notificationsTab = new Tab("🔔 Notifications");
        notificationView = new NotificationView();
        notificationsTab.setContent(notificationView.getRoot());

        Tab reportsTab = new Tab("📈 Rapports");
        ReportView reportView = new ReportView();
        reportsTab.setContent(reportView.getRoot());

        tabPane.getTabs().addAll(dashboardTab, eventsTab, participantsTab, notificationsTab, reportsTab);

        return tabPane;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.getStyleClass().add("status-bar");
        statusBar.setPadding(new Insets(5, 15, 5, 15));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.setSpacing(20);

        Label statusLabel = new Label("Prêt");
        statusLabel.getStyleClass().add("status-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label timeLabel = new Label();
        timeLabel.getStyleClass().add("time-label");
        controller.updateTimeLabel(timeLabel);

        statusBar.getChildren().addAll(statusLabel, spacer, timeLabel);

        return statusBar;
    }

    public BorderPane getRoot() {
        return root;
    }

    public NotificationView getNotificationView() {
        return notificationView;
    }
}