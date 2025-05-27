package com.evenements.model;

import java.time.LocalDateTime;

/**
 * Represents a conference event, extending the base Evenement class.
 */
public class Conference extends Evenement {
    private String theme;

    public Conference() {
        super();
    }

    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
    }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
}