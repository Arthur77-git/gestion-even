package com.evenements.strategy;

import com.evenements.model.Evenement;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Strategy to sort events by date in ascending order.
 */
public class TriParDateStrategy implements TriStrategy {
    @Override
    public List<Evenement> trier(List<Evenement> evenements) {
        return evenements.stream()
                .sorted(Comparator.comparing(Evenement::getDate))
                .collect(Collectors.toList());
    }
}