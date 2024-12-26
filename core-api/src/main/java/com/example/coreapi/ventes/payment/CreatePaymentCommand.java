package com.example.coreapi.ventes.payment;

import java.time.LocalDateTime;
import java.util.Set;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreatePaymentCommand(@TargetAggregateIdentifier String paymentId,
        TypePayment typePayment,
        Double montant,
        LocalDateTime datePayment,
        StatusPaiment statusPayment,
        String userId,
        Set<String> produitIds) {

}
