package com.example.coreapi.boutique.stock.commands;

import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockCommand {
    @TargetAggregateIdentifier
    private String stockId;
    private String productId;
    private int quantity;
}
