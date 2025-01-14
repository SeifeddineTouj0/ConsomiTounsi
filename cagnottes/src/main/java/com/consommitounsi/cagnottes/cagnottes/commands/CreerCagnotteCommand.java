package com.consommitounsi.cagnottes.cagnottes.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Getter
public class CreerCagnotteCommand {
    @TargetAggregateIdentifier
    private final String cagnotteId;
    private final String nom;
    private final String description;
    private final double montantCible;

    public CreerCagnotteCommand(String cagnotteId, String nom, String description, double montantCible) {
        this.cagnotteId = cagnotteId;
        this.nom = nom;
        this.description = description;
        this.montantCible = montantCible;
    }

    // Getters
}
