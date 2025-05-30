package com.evenements.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {
    private Timeline clockTimeline;

    public MainController() {
    }

    public void updateTimeLabel(Label timeLabel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        clockTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeLabel.setText(LocalDateTime.now().format(formatter));
                })
        );
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();

        timeLabel.setText(LocalDateTime.now().format(formatter));
    }

    public void cleanup() {
        if (clockTimeline != null) {
            clockTimeline.stop();
        }
    }
}