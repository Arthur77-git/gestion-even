package com.evenements.exception;

/**
 * Exception thrown when the maximum capacity of an event is reached.
 */
public class CapaciteMaxAtteinteException extends Exception {
    private static final long serialVersionUID = 1L;

    public CapaciteMaxAtteinteException(String message) {
        super(message);
    }

    public CapaciteMaxAtteinteException(String message, Throwable cause) {
        super(message, cause);
    }
}