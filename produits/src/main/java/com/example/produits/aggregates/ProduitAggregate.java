package com.example.produits.aggregates;


import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import com.example.coreapi.produits.commands.CreateProductCommand;
import com.example.coreapi.produits.events.ProductCreatedEvent;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class ProduitAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String category;
    private RayonType storageType;
    private float weight;
    private float price;


    @CommandHandler
    public ProduitAggregate(CreateProductCommand command){
        apply(new ProductCreatedEvent(command.getId(),command.getName(), command.getCategory(), command.getStorageType(),command.getWeight(), command.getPrice()));
    }


    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.category = event.getCategory();
        this.storageType = event.getStorageType();
        this.weight = event.getWeight();
        this.price = event.getPrice();
    }
}
