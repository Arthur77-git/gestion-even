package com.evenements.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = MainWindow.getInstance();
        Scene scene = new Scene(mainWindow.getRoot(), 1200, 800);

        // Load CSS if available
        String cssPath = getClass().getResource("/styles/main-styles.css") != null
                ? getClass().getResource("/styles/main-styles.css").toExternalForm()
                : null;
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        } else {
            System.err.println("Warning: CSS file /styles/main-styles.css.css not found in resources");
        }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion d'Événements");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}