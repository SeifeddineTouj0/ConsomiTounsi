package com.example.coreapi.ventes.facture;

import java.time.LocalDate;
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
public class FactureInfo {
    @Id
    String factureId;
    LocalDate dateFacture;
    TypeFacture typeFacture;
    String paymentId;
    String user;
    Double montant;

    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> products;
}
