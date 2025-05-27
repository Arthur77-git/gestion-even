package com.evenements.exception;

/**
 * Exception thrown when an event with the same ID already exists.
 */
public class EvenementDejaExistantException extends Exception {
    public EvenementDejaExistantException(String message) {
        super(message);
    }
}