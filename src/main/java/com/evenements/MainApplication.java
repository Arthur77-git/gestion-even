package com.evenements;

import com.evenements.exception.*;
import com.evenements.factory.EvenementFactory;
import com.evenements.model.*;
import com.evenements.serialization.JsonSerializer;
import com.evenements.serialization.XmlSerializer;
import com.evenements.service.EmailNotificationService;
import com.evenements.service.GestionEvenements;
import com.evenements.service.SMSNotificationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Main application class to demonstrate the event management system.
 */
public class MainApplication {

    public static void main(String[] args) {
        try {
            demonstrationSysteme();
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void demonstrationSysteme() throws Exception {
        System.out.println("=".repeat(50));
        System.out.println("DÉMONSTRATION SYSTÈME DE GESTION D'ÉVÉNEMENTS");
        System.out.println("=".repeat(50));

        // Initialisation des services
        GestionEvenements gestion = GestionEvenements.getInstance();
        EmailNotificationService emailService = new EmailNotificationService();
        SMSNotificationService smsService = new SMSNotificationService();

        // Création d'événements avec Factory Pattern
        System.out.println("\n1. Création d'événements (Factory Pattern)");
        Conference conference = (Conference) EvenementFactory.creerEvenement(
                "conference", "CONF001", "Conférence IA 2025",
                LocalDateTime.now().plusDays(30), "Paris La Défense", 150,
                "Intelligence Artificielle et Futur"
        );

        Concert concert = (Concert) EvenementFactory.creerEvenement(
                "concert", "CONC001", "Festival Rock Summer",
                LocalDateTime.now().plusDays(45), "Stade de France", 80000,
                "Metallica", "Heavy Metal"
        );

        // Ajout d'intervenants à la conférence
        Intervenant intervenant1 = new Intervenant("INT001", "Dr. Marie Dubois", "Machine Learning");
        Intervenant intervenant2 = new Intervenant("INT002", "Prof. Jean Martin", "Réseaux de Neurones");
        conference.ajouterIntervenant(intervenant1);
        conference.ajouterIntervenant(intervenant2);

        // Ajout des événements (Singleton Pattern)
        gestion.ajouterEvenement(conference);
        gestion.ajouterEvenement(concert);

        System.out.println("✓ Événements créés et ajoutés au système");

        // Création de participants et organisateurs
        System.out.println("\n2. Création de participants et organisateurs");
        Participant participant1 = EvenementFactory.creerParticipant("P001", "Alice Dupont", "alice@email.com", emailService);
        Participant participant2 = EvenementFactory.creerParticipant("P002", "Bob Martin", "bob@email.com", smsService);
        Organisateur organisateur = EvenementFactory.creerOrganisateur("ORG001", "Claire Admin", "admin@events.com", emailService);

        // Inscription des participants (Observer Pattern)
        System.out.println("\n3. Inscription des participants (Observer Pattern)");
        conference.ajouterParticipant(participant1);
        conference.ajouterParticipant(participant2);
        concert.ajouterParticipant(participant1);

        organisateur.ajouterEvenementOrganise(conference.getId());

        // Affichage des détails
        System.out.println("\n4. Affichage des détails des événements");
        conference.afficherDetails();
        System.out.println();
        concert.afficherDetails();

        // Test des exceptions personnalisées
        System.out.println("\n5. Test des exceptions personnalisées");
        try {
            // Test capacité max
            Conference petitEvent = new Conference("SMALL001", "Petit Event",
                    LocalDateTime.now().plusDays(10), "Salle XS", 1, "Test");
            gestion.ajouterEvenement(petitEvent);

            petitEvent.ajouterParticipant(participant1);
            petitEvent.ajouterParticipant(participant2); // Doit lever une exception

        } catch (CapaciteMaxAtteinteException e) {
            System.out.println("✓ Exception capturée: " + e.getMessage());
        }

        try {
            // Test événement déjà existant
            gestion.ajouterEvenement(conference); // Doit lever une exception
        } catch (EvenementDejaExistantException e) {
            System.out.println("✓ Exception capturée: " + e.getMessage());
        }

        // Recherche avec Streams et Lambdas
        System.out.println("\n6. Recherche avec Streams et Lambdas");
        List<Evenement> conferencesIA = gestion.rechercherParNom("IA");
        System.out.println("Événements trouvés pour 'IA': " + conferencesIA.size());

        List<Evenement> evenementsParis = gestion.rechercherParLieu("Paris");
        System.out.println("Événements à Paris: " + evenementsParis.size());

        // Modification d'événement (Observer Pattern)
        System.out.println("\n7. Modification d'événement (notifications automatiques)");
        conference.modifierEvenement("Conférence IA 2025 - ÉDITION SPÉCIALE",
                LocalDateTime.now().plusDays(35), "Paris Expo");

        // Programmation asynchrone
        System.out.println("\n8. Notifications asynchrones");
        CompletableFuture<Void> notification1 = emailService.envoyerNotificationAsync(
                "Rappel: Conférence IA dans 30 jours!"
        );
        CompletableFuture<Void> notification2 = emailService.envoyerNotificationAsync(
                "Nouvelles places disponibles pour le concert!"
        );

        CompletableFuture.allOf(notification1, notification2).join();
        System.out.println("✓ Toutes les notifications asynchrones ont été envoyées");

        // Sérialisation JSON
        System.out.println("\n9. Sérialisation JSON");
        JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.sauvegarderEvenements(gestion.getTousLesEvenements(), "evenements.json");
        System.out.println("✓ Événements sauvegardés en JSON");

        List<Evenement> evenementsChargesJson = jsonSerializer.chargerEvenements("evenements.json");
        System.out.println("✓ " + evenementsChargesJson.size() + " événements chargés depuis JSON");

        // Sérialisation XML
        System.out.println("\n10. Sérialisation XML");
        XmlSerializer xmlSerializer = new XmlSerializer();
        xmlSerializer.sauvegarderEvenements(gestion.getTousLesEvenements(), "evenements.xml");
        System.out.println("✓ Événements sauvegardés en XML");

        List<Evenement> evenementsChargesXml = xmlSerializer.chargerEvenements("evenements.xml");
        System.out.println("✓ " + evenementsChargesXml.size() + " événements chargés depuis XML");

        // Annulation d'événement
        System.out.println("\n11. Annulation d'événement (notifications automatiques)");
        concert.annuler();

        // Statistiques finales
        System.out.println("\n12. Statistiques du système");
        System.out.println("Nombre total d'événements: " + gestion.getNombreEvenements());
        System.out.println("Événements actifs: " + gestion.getEvenementsActifs().size());
        System.out.println("Participants conférence: " + conference.getParticipants().size());
        System.out.println("Événements organisés par " + organisateur.getNom() + ": " +
                organisateur.getEvenementsOrganisesIds().size());

        System.out.println("\n" + "=".repeat(50));
        System.out.println("DÉMONSTRATION TERMINÉE AVEC SUCCÈS");
        System.out.println("=".repeat(50));
    }
}