package com.example.coreapi.ventes.facture;

import java.time.LocalDate;

public record FactureCreatedEvent(
        String factureId,
        LocalDate dateFacture,
        TypeFacture typeFacture,
        String paymentId) {

}