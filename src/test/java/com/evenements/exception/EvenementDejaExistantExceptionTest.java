package com.evenements.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EvenementDejaExistantExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "L'événement existe déjà";
        EvenementDejaExistantException exception = new EvenementDejaExistantException(message);
        
        assertEquals(message, exception.getMessage(), "Le message de l'exception doit correspondre à celui passé au constructeur");
    }

    @Test
    void testExceptionInheritance() {
        EvenementDejaExistantException exception = new EvenementDejaExistantException("Test");
        
        assertTrue(exception instanceof Exception, "L'exception doit être une instance de Exception");
    }

    @Test
    void testNullMessage() {
        EvenementDejaExistantException exception = new EvenementDejaExistantException(null);
        
        assertNull(exception.getMessage(), "Le message de l'exception doit être null si null est passé au constructeur");
    }
}