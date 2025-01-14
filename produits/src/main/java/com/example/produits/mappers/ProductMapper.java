package com.example.produits.mappers;

import com.example.coreapi.produits.queries.ProductInfo;
import com.example.produits.entities.Produit;

public class ProductMapper {

    public static ProductInfo toDTO(Produit produit){
        return ProductInfo.builder().
                id(produit.getId()).
                name(produit.getName()).
                category(produit.getCategory()).
                storageType(produit.getStorageType()).
                weight(produit.getWeight()).
                price(produit.getPrice()).
                build();
    }
}
