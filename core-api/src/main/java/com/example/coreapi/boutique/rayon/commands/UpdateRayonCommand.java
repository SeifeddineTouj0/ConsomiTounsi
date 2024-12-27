package com.example.coreapi.boutique.rayon.commands;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRayonCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private RayonType type;
    private List<String> productIds;
    private String position;
}
