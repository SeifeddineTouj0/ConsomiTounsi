package com.example.coreapi.produits.events;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {
    private String id;
    private String name;
    private String barCode;
    private String category;
    private RayonType storageType;
    private float weight;
    private float price;
}
