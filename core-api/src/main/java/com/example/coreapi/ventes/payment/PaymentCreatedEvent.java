package com.example.coreapi.ventes.payment;

import java.time.LocalDateTime;
import java.util.Set;

public record PaymentCreatedEvent(String paymentId, TypePayment typePayment, Double montant,
                LocalDateTime datePayment, StatusPaiment statusPayment, String userId, Set<String> produitIds) {
}