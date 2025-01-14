package com.example.coreapi.produits.events;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDeletedEvent {
    String id;
}
