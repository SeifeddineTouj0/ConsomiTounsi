package com.example.coreapi.boutique.rayon.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class AssignProductToRayonCommand {
    @TargetAggregateIdentifier
    private String rayonId;
    private String productId;
    private Integer quantity;
}