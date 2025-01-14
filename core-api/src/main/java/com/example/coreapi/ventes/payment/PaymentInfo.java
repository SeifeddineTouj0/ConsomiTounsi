package com.example.coreapi.ventes.payment;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PaymentInfo {
    @Id
    String paymentId;
    TypePayment typePayment;
    Double montant;
    LocalDateTime datePayment;
    StatusPaiment statusPayment;
    String user;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<PurchasedProduct> products;
}
