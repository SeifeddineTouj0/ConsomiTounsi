package com.example.coreapi.boutique.stock.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStockCommand {
    @TargetAggregateIdentifier
    private String stockId; // Identifier of the existing Stock aggregate to delete
}
