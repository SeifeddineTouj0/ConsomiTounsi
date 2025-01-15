package com.example.coreapi.ventes.payment;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreatedEvent {
    String paymentId;
    TypePayment typePayment;
    Double montant;
    LocalDateTime datePayment;
    StatusPaiment statusPayment;
    String userId;
    Set<PurchasedProduct> produitIds;
    double userAdressLong;
    double userAdressLat;
}