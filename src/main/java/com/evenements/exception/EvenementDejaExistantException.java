package com.evenements.exception;

/**
 * Exception thrown when an event with the same ID already exists.
 */
public class EvenementDejaExistantException extends Exception {
    private static final long serialVersionUID = 1L;

    public EvenementDejaExistantException(String message) {
        super(message);
    }

    public EvenementDejaExistantException(String message, Throwable cause) {
        super(message, cause);
    }
}