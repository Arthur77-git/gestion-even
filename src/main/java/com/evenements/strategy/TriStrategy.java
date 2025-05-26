package com.evenements.strategy;

import com.evenements.model.Evenement;
import java.util.List;

public interface TriStrategy {
    List<Evenement> trier(List<Evenement> evenements);
}