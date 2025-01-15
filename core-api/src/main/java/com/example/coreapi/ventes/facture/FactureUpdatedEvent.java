package com.example.coreapi.ventes.facture;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureUpdatedEvent {
    String factureId;
    LocalDate dateFacture;
    TypeFacture typeFacture;
    String paymentId;
    

}