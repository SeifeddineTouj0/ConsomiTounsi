package com.example.coreapi.produits.commands;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String barCode;
    private String category;
    private RayonType storageType;
    private float weight;
    private float price;
}
