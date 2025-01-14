package com.example.coreapi.ventes.facture;

import java.time.LocalDate;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateFactureCommand(
        @TargetAggregateIdentifier String factureId,
        LocalDate dateFacture,
        TypeFacture typeFacture,
        String paymentId) {
}
