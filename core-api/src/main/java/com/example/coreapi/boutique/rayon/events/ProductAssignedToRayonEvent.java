package com.example.coreapi.boutique.rayon.events;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductAssignedToRayonEvent {
    private String rayonId;
    private String productId;
    private Integer quantity;
}
