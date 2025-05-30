package com.evenements.gui;

import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.singleton.GestionEvenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ReportController {
    private GestionEvenements gestionEvenements;

    public ReportController() {
        this.gestionEvenements = GestionEvenements.getInstance();
    }

    public ObservableList<ReportView.ReportItem> generateReport(String type, String status) {
        ObservableList<ReportView.ReportItem> items = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (type.equals("Événements")) {
            for (Evenement event : gestionEvenements.getTousLesEvenements()) {
                if (status.equals("Tous") ||
                        (status.equals("Actifs") && !event.isAnnule()) ||
                        (status.equals("Annulés") && event.isAnnule())) {
                    String detail = String.format("Date: %s, Lieu: %s, Capacité: %d",
                            event.getDate().format(formatter), event.getLieu(), event.getCapaciteMax());
                    items.add(new ReportView.ReportItem(
                            event.getId(),
                            event.getNom(),
                            detail,
                            event.isAnnule() ? "Annulé" : "Actif"
                    ));
                }
            }
        } else if (type.equals("Participants")) {
            for (Evenement event : gestionEvenements.getTousLesEvenements()) {
                if (status.equals("Tous") ||
                        (status.equals("Actifs") && !event.isAnnule()) ||
                        (status.equals("Annulés") && event.isAnnule())) {
                    for (Participant participant : event.getParticipants()) {
                        String detail = String.format("Événement: %s, Email: %s",
                                event.getNom(), participant.getEmail());
                        items.add(new ReportView.ReportItem(
                                participant.getId(),
                                participant.getNom(),
                                detail,
                                event.isAnnule() ? "Annulé" : "Actif"
                        ));
                    }
                }
            }
        }

        return items;
    }

    public void exportToCSV(ObservableList<ReportView.ReportItem> items) {
        try (FileWriter writer = new FileWriter("rapport.csv")) {
            writer.append("ID,Nom,Détail,Statut\n");
            for (ReportView.ReportItem item : items) {
                writer.append(String.format("%s,%s,%s,%s\n",
                        item.getId(),
                        item.getName().replace(",", ";"),
                        item.getDetail().replace(",", ";"),
                        item.getStatus()));
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation CSV: " + e.getMessage());
        }
    }
}