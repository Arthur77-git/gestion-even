package com.evenements.observer;

/**
 * Interface for observers (participants) that receive updates from events.
 */
public interface ParticipantObserver {
    void update(String message);
}