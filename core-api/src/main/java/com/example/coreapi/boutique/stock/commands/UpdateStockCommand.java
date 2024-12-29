package com.example.coreapi.boutique.stock.commands;

import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UpdateStockCommand {
    @TargetAggregateIdentifier
    private String stockId;
    private String productId;
    private Integer quantity;
}