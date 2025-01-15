package com.example.coreapi.produits.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteProductCommand {
    @TargetAggregateIdentifier
    String id;
}
