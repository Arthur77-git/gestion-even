package com.evenements.observer;

public interface EvenementObservable {
    void ajouterObserver(ParticipantObserver observer);
    void supprimerObserver(ParticipantObserver observer);
    void notifierObservers(String message);
}