package com.evenements.observer;

/**
 * Interface for observable events that can notify observers of changes.
 */
public interface EvenementObservable {
    void ajouterObserver(ParticipantObserver observer);
    void supprimerObserver(ParticipantObserver observer);
    void notifierObservers(String message);
}