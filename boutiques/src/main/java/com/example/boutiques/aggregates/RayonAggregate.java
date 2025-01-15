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
import com.example.coreapi.produits.queries.FetchproductByIdQuery;
import com.example.coreapi.produits.queries.ProductInfo;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private QueryGateway queryGateway;

    //Create Rayon
    @CommandHandler
    public RayonAggregate(CreateRayonCommand command, QueryGateway queryGateway) {
        // Validate products
        if (command.getProductIds() != null && !command.getProductIds().isEmpty()) {
            boolean allValid = command.getProductIds().stream().allMatch(productId -> {
                ProductInfo product = queryGateway.query(
                        new FetchproductByIdQuery(productId),
                        ProductInfo.class
                ).join();

                return product != null && product.getStorageType() == command.getType();
            });

            if (!allValid) {
                throw new IllegalArgumentException("One or more products are invalid or have mismatched storage types.");
            }
        }

        AggregateLifecycle.apply(new RayonCreatedEvent(
                command.getRayonId(),
                command.getName(),
                command.getType(),
                command.getProductIds(),
                command.getPosition()
        ));
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
        // Validate products
        if (command.getProductIds() != null && !command.getProductIds().isEmpty()) {
            boolean allValid = command.getProductIds().stream().allMatch(productId -> {
                ProductInfo product = this.queryGateway.query(
                        new FetchproductByIdQuery(productId),
                        ProductInfo.class
                ).join();

                return product != null && product.getStorageType() == command.getType();
            });

            if (!allValid) {
                throw new IllegalArgumentException("One or more products are invalid or have mismatched storage types.");
            }
        }
        AggregateLifecycle.apply(new RayonUpdatedEvent(
                command.getId(),
                command.getName(),
                command.getType(),
                command.getProductIds(),
                command.getPosition()
        ));
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

    //Assign Product To Rayon
    @CommandHandler
    public void handle(AssignProductToRayonCommand command, QueryGateway queryGateway) {
        // Validate product existence and storage type
        ProductInfo product = this.queryGateway.query(
                new FetchproductByIdQuery(command.getProductId()),
                ProductInfo.class
        ).join();

        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + command.getProductId() + " does not exist.");
        }

        if (product.getStorageType() != this.type) {
            throw new IllegalArgumentException(
                    "Product storage type " + product.getStorageType() + " does not match Rayon's storage type " + this.type
            );
        }

        // Apply the ProductAssignedToRayonEvent if validation passes
        AggregateLifecycle.apply(new ProductAssignedToRayonEvent(
                command.getRayonId(),
                command.getProductId(),
                command.getQuantity()
        ));
    }

    @EventSourcingHandler
    public void on(ProductAssignedToRayonEvent event) {
    }

}