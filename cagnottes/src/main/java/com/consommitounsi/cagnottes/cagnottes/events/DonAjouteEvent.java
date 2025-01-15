package com.consommitounsi.cagnottes.cagnottes.events;

import lombok.Getter;

@Getter
public class DonAjouteEvent {
    private final String cagnotteId;
    private final double montant;

    public DonAjouteEvent(String cagnotteId, double montant) {
        this.cagnotteId = cagnotteId;
        this.montant = montant;
    }

}
