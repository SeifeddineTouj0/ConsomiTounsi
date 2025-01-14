package com.consommitounsi.cagnottes.cagnottes.commands;


import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class SupprimerCagnotteCommand {
    @TargetAggregateIdentifier
    private final String cagnotteId;

    public SupprimerCagnotteCommand(String cagnotteId) {
        this.cagnotteId = cagnotteId;
    }
}
