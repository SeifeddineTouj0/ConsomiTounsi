package com.example.produits.entities;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "products")
public class Produit {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private RayonType storageType;

    @Column(nullable = false)
    private float weight;

    @Column(nullable = false)
    private float price;



}
