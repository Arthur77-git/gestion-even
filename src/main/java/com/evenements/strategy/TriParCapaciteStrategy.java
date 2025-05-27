package com.evenements.strategy;

import com.evenements.model.Evenement;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Strategy to sort events by maximum capacity in descending order.
 */
public class TriParCapaciteStrategy implements TriStrategy {
    @Override
    public List<Evenement> trier(List<Evenement> evenements) {
        return evenements.stream()
                .sorted(Comparator.comparingInt(Evenement::getCapaciteMax).reversed())
                .collect(Collectors.toList());
    }
}