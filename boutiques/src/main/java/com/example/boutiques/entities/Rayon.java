package com.example.boutiques.entities;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rayons")
public class Rayon {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private RayonType type; // NORMAL or REFRIGERATOR

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> productIds; // IDs of products placed in the rayon

    @Column(nullable = false)
    private String position; // Position in the store (e.g., "Aisle 3")
}