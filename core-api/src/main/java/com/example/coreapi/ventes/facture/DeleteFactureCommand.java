package com.example.coreapi.ventes.facture;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFactureCommand {
        @TargetAggregateIdentifier
        String factureId;
}
