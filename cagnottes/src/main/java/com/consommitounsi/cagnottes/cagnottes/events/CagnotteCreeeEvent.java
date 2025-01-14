package com.consommitounsi.cagnottes.cagnottes.events;

import lombok.Getter;

@Getter
public class CagnotteCreeeEvent {
    private final String cagnotteId;
    private final String nom;
    private final String description;
    private final double montantCible;

    public CagnotteCreeeEvent(String cagnotteId, String nom, String description, double montantCible) {
        this.cagnotteId = cagnotteId;
        this.nom = nom;
        this.description = description;
        this.montantCible = montantCible;
    }

}

