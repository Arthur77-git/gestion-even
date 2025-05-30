package com.evenements.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationView {
    private BorderPane root;
    private NotificationController controller;
    private ListView<NotificationItem> notificationsList;
    private ObservableList<NotificationItem> items;

    public NotificationView() {
        this.controller = new NotificationController();
        this.items = FXCollections.observableArrayList();
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("notification-container");
        root.setPadding(new Insets(20));

        VBox header = createHeader();
        root.setTop(header);

        SplitPane mainContent = createMainContent();
        root.setCenter(mainContent);
    }

    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Notifications");
        title.getStyleClass().add("panel-title");
        Button clearButton = new Button("Effacer Tout");
        clearButton.getStyleClass().addAll("btn", "btn-secondary");
        clearButton.setOnAction(e -> items.clear());
        header.getChildren().addAll(title, clearButton);
        return header;
    }

    private SplitPane createMainContent() {
        SplitPane splitPane = new SplitPane();
        notificationsList = new ListView<>(items);
        notificationsList.getStyleClass().add("notifications-list");
        notificationsList.setCellFactory(lv -> new ListCell<NotificationItem>() {
            @Override
            protected void updateItem(NotificationItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                            ": " + item.getMessage());
                }
            }
        });
        splitPane.getItems().add(notificationsList);
        return splitPane;
    }

    public void addNotification(String message) {
        items.add(new NotificationItem(message, LocalDateTime.now()));
    }

    public BorderPane getRoot() {
        return root;
    }

    public static class NotificationItem {
        private String message;
        private LocalDateTime timestamp;

        public NotificationItem(String message, LocalDateTime timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}