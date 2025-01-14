package com.example.coreapi.produits.queries;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ProductInfo implements Serializable {
    private String id;
    private String name;
    private String barCode;
    private String category;
    private RayonType storageType;
    private float weight;
    private float price;
}
