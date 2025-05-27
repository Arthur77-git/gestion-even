package com.evenements.service;

import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.model.Evenement;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Singleton service for managing events.
 */
public class GestionEvenements {
    private static volatile GestionEvenements instance;
    private final Map<String, Evenement> evenements;

    private GestionEvenements() {
        this.evenements = new ConcurrentHashMap<>();
    }

    public static GestionEvenements getInstance() {
        if (instance == null) {
            synchronized (GestionEvenements.class) {
                if (instance == null) {
                    instance = new GestionEvenements();
                }
            }
        }
        return instance;
    }

    public void ajouterEvenement(Evenement evenement) throws EvenementDejaExistantException {
        if (evenements.containsKey(evenement.getId())) {
            throw new EvenementDejaExistantException("Un événement avec l'ID " + evenement.getId() + " existe déjà");
        }
        evenements.put(evenement.getId(), evenement);
    }

    public boolean supprimerEvenement(String id) {
        Evenement evenement = evenements.remove(id);
        if (evenement != null) {
            evenement.annuler();
            return true;
        }
        return false;
    }

    public Optional<Evenement> rechercherEvenement(String id) {
        return Optional.ofNullable(evenements.get(id));
    }

    public List<Evenement> rechercherParNom(String nom) {
        return evenements.values().stream()
                .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Evenement> rechercherParLieu(String lieu) {
        return evenements.values().stream()
                .filter(e -> e.getLieu().toLowerCase().contains(lieu.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Evenement> getEvenementsActifs() {
        return evenements.values().stream()
                .filter(e -> !e.isAnnule())
                .collect(Collectors.toList());
    }

    public List<Evenement> getTousLesEvenements() {
        return new ArrayList<>(evenements.values());
    }

    public void viderTousLesEvenements() {
        evenements.clear();
    }

    public int getNombreEvenements() {
        return evenements.size();
    }
}