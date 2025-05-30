package com.evenements.factory;

import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class EvenementFactory {
    public static Evenement createEvenement(String type, String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String... details) {
        switch (type.toLowerCase()) {
            case "conference":
                String theme = details.length > 0 ? details[0] : "";
                List<String> intervenants = details.length > 1 ? Arrays.asList(details[1].split(",")) : Arrays.asList();
                return new Conference(id, nom, date, lieu, capaciteMax, theme, intervenants);
            case "concert":
                String artiste = details.length > 0 ? details[0] : "";
                String genre = details.length > 1 ? details[1] : "";
                return new Concert(id, nom, date, lieu, capaciteMax, artiste, genre);
            default:
                return new Evenement(id, nom, date, lieu, capaciteMax) {
                    @Override
                    public void afficherDetails() {
                        System.out.println("Événement: " + getNom());
                    }
                };
        }
    }
}