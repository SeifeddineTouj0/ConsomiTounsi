package com.example.boutiques.aggregates;

import com.example.coreapi.boutique.rayon.commands.AssignProductToRayonCommand;
import com.example.coreapi.boutique.rayon.commands.CreateRayonCommand;
import com.example.coreapi.boutique.rayon.commands.DeleteRayonCommand;
import com.example.coreapi.boutique.rayon.commands.UpdateRayonCommand;
import com.example.coreapi.boutique.rayon.events.ProductAssignedToRayonEvent;
import com.example.coreapi.boutique.rayon.events.RayonCreatedEvent;
import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import com.example.coreapi.boutique.rayon.events.RayonDeletedEvent;
import com.example.coreapi.boutique.rayon.events.RayonUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
@NoArgsConstructor
public class RayonAggregate {
    @AggregateIdentifier
    private String rayonId;
    private String name;
    private RayonType type;
    private List<String> productIds;
    private String position;

    //Create Rayon
    @CommandHandler
    public RayonAggregate(CreateRayonCommand command) {
        AggregateLifecycle.apply(new RayonCreatedEvent(command.getRayonId(), command.getName(), command.getType(), command.getProductIds(), command.getPosition()));
    }

    @EventSourcingHandler
    public void on(RayonCreatedEvent event) {
        this.rayonId = event.getRayonId();
        this.name = event.getName();
        this.type = event.getType();
        this.productIds = event.getProductIds();
        this.position = event.getPosition();
    }

    //Update Rayon
    @CommandHandler
    public void handle(UpdateRayonCommand command) {
        AggregateLifecycle.apply(new RayonUpdatedEvent(command.getId(), command.getName(), command.getType(), command.getProductIds(), command.getPosition()));
    }

    @EventSourcingHandler
    public void on(RayonUpdatedEvent event) {
        this.name = event.getName();
        this.type = event.getType();
        this.productIds = event.getProductIds();
        this.position = event.getPosition();
    }

    //Delete Rayon
    @CommandHandler
    public void handle(DeleteRayonCommand command) {
        AggregateLifecycle.apply(new RayonDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(RayonDeletedEvent event) {
    }

}