package com.example.coreapi.delivery;

import com.example.coreapi.produits.queries.ProductInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class OrderDetailsDTO {
    private final Double targetLat;
    private final Double targetLng;
    private final List<ProductInfo> products;
}
