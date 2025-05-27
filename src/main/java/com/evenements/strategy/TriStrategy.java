package com.evenements.strategy;

import com.evenements.model.Evenement;
import java.util.List;

/**
 * Interface for strategies to sort events.
 */
public interface TriStrategy {
    List<Evenement> trier(List<Evenement> evenements);
}