package com.example.coreapi.ventes.facture;

import java.time.LocalDate;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFactureCommand {
        @TargetAggregateIdentifier
        String factureId;
        LocalDate dateFacture;
        TypeFacture typeFacture;
        String paymentId;
}
