package com.example.coreapi.ventes.payment;

import java.time.LocalDateTime;
import java.util.Set;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentCommand {
    @TargetAggregateIdentifier
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
