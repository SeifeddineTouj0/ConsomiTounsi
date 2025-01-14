package com.consommitounsi.cagnottes.cagnottes.commands;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class AjouterDonCommand {

    @TargetAggregateIdentifier
    private final String cagnotteId;
    private final double montant;

    public AjouterDonCommand(String cagnotteId, double montant) {
        this.cagnotteId = cagnotteId;
        this.montant = montant;
    }


    // Getters
}
