package com.example.coreapi.boutique.rayon.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRayonCommand {
    @TargetAggregateIdentifier
    private String id;
}
