package com.example.coreapi.boutique.stock.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDeletedEvent {
    private String stockId;
}