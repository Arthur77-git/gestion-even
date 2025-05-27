package com.evenements.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParticipantDejaInscritExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Le participant est déjà inscrit";
        ParticipantDejaInscritException exception = new ParticipantDejaInscritException(message);
        
        assertEquals(message, exception.getMessage(), "Le message de l'exception doit correspondre à celui passé au constructeur");
    }

    @Test
    void testExceptionInheritance() {
        ParticipantDejaInscritException exception = new ParticipantDejaInscritException("Test");
        
        assertTrue(exception instanceof Exception, "L'exception doit être une instance de Exception");
    }

    @Test
    void testNullMessage() {
        ParticipantDejaInscritException exception = new ParticipantDejaInscritException(null);
        
        assertNull(exception.getMessage(), "Le message de l'exception doit être null si null est passé au constructeur");
    }
}