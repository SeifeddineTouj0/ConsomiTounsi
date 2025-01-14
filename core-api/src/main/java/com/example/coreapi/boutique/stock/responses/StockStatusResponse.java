package com.example.coreapi.boutique.stock.responses;

import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockStatusResponse {
    private String stockId;
    private StockStatus status;
    private int quantity;
}