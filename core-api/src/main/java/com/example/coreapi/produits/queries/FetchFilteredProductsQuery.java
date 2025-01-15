package com.example.coreapi.produits.queries;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FetchFilteredProductsQuery {
    private String category;
    private RayonType storageType;
    private Float  maxWeight;
    private Float  minWeight;
    private Float  maxPrice;
    private Float  minPrice;
}
