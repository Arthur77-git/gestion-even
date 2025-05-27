package com.evenements.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CapaciteMaxAtteinteExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Capacité maximale atteinte";
        CapaciteMaxAtteinteException exception = new CapaciteMaxAtteinteException(message);
        
        assertEquals(message, exception.getMessage(), "Le message de l'exception doit correspondre à celui passé au constructeur");
    }

    @Test
    void testExceptionInheritance() {
        CapaciteMaxAtteinteException exception = new CapaciteMaxAtteinteException("Test");
        
        assertTrue(exception instanceof Exception, "L'exception doit être une instance de Exception");
    }

    @Test
    void testNullMessage() {
        CapaciteMaxAtteinteException exception = new CapaciteMaxAtteinteException(null);
        
        assertNull(exception.getMessage(), "Le message de l'exception doit être null si null est passé au constructeur");
    }
}