package com.example.coreapi.ventes.facture;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.example.coreapi.ventes.payment.PurchasedProduct;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FactureInfo {
    @Id
    String factureId;
    LocalDate dateFacture;
    TypeFacture typeFacture;
    String paymentId;
    String user;
    String username;
    String email;
    Double montant;
    Double deliveryFees;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "payment_products", joinColumns = @JoinColumn(name = "payment_id"))
    Set<PurchasedProduct> productsQuantites;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> productsNames;
}
