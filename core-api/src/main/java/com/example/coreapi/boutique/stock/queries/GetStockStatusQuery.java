package com.example.coreapi.boutique.stock.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStockStatusQuery {
    private String productId;
}
