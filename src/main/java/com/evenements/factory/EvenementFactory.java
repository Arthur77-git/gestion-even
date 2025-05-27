package com.evenements.factory;

import com.evenements.model.*;
import com.evenements.service.NotificationService;

import java.time.LocalDateTime;

public class EvenementFactory {

    public static Evenement creerEvenement(String type, String id, String nom, LocalDateTime date,
                                           String lieu, int capaciteMax, String... params) {
        switch (type.toLowerCase()) {
            case "conference":
                String theme = params.length > 0 ? params[0] : "Thème général";
                return new Conference(id, nom, date, lieu, capaciteMax, theme);
            case "concert":
                String artiste = params.length > 0 ? params[0] : "Artiste inconnu";
                String genre = params.length > 1 ? params[1] : "Genre inconnu";
                return new Concert(id, nom, date, lieu, capaciteMax, artiste, genre);
            default:
                throw new IllegalArgumentException("Type d'événement non supporté: " + type);
        }
    }

    public static Participant creerParticipant(String id, String nom, String email, NotificationService notificationService) {
        return new Participant(id, nom, email/*, notificationService*/);
    }

    public static Organisateur creerOrganisateur(String id, String nom, String email, NotificationService notificationService) {
        return new Organisateur(id, nom, email/*, notificationService*/);
    }
}