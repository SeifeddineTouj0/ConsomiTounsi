package com.example.coreapi.ventes.facture;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DeleteFactureCommand(
        @TargetAggregateIdentifier String factureId) {
}
