package com.evenements.gui;

public class NotificationController {
    public void sendNotification(String message) {
        // Access NotificationView instance to add notification
        // For simplicity, assume a static method to get the view
        MainWindow.getInstance().getNotificationView().addNotification(message);
    }
}