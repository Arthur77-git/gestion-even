package com.evenements.exception;

/**
 * Exception thrown when a participant is already registered for an event.
 */
public class ParticipantDejaInscritException extends Exception {
    private static final long serialVersionUID = 1L;

    public ParticipantDejaInscritException(String message) {
        super(message);
    }

    public ParticipantDejaInscritException(String message, Throwable cause) {
        super(message, cause);
    }
}