package com.example.coreapi.boutique.stock.events;

import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdatedEvent {
    private String stockId;
    private String productId;
    private Integer quantity;
    private StockStatus status;
}
