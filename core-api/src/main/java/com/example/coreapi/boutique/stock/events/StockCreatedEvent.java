package com.example.coreapi.boutique.stock.events;

import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockCreatedEvent {
    private String stockId;
    private String productId;
    private int quantity;
    private StockStatus status;
}