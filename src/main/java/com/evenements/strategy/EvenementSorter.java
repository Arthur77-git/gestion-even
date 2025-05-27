package com.evenements.strategy;

import com.evenements.model.Evenement;
import java.util.List;

/**
 * Class to sort events using a specified strategy.
 */
public class EvenementSorter {
    private TriStrategy strategy;

    public EvenementSorter(TriStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(TriStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Evenement> trier(List<Evenement> evenements) {
        return strategy.trier(evenements);
    }
}