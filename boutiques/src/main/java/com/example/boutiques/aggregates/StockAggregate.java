package com.example.boutiques.aggregates;


import com.example.coreapi.boutique.stock.commands.CreateStockCommand;
import com.example.coreapi.boutique.stock.commands.UpdateStockCommand;
import com.example.coreapi.boutique.stock.commands.DeleteStockCommand;
import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import com.example.coreapi.boutique.stock.events.StockCreatedEvent;
import com.example.coreapi.boutique.stock.events.StockUpdatedEvent;
import com.example.coreapi.boutique.stock.events.StockDeletedEvent;
import com.example.coreapi.produits.queries.FetchproductByIdQuery;
import com.example.coreapi.produits.queries.ProductInfo;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate
@NoArgsConstructor
public class StockAggregate {

    @AggregateIdentifier
    private String stockId;
    private String productId;
    private int quantity;
    private StockStatus status;

    @Autowired
    private QueryGateway queryGateway;

    //Create Stock
    @CommandHandler
    public StockAggregate(CreateStockCommand command, QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
        // Validate product existence
        ProductInfo product = this.queryGateway.query(
                new FetchproductByIdQuery(command.getProductId()),
                ProductInfo.class
        ).join();

        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + command.getProductId() + " does not exist.");
        }

        StockStatus status = determineStockStatus(command.getQuantity());
        apply(new StockCreatedEvent(command.getStockId(), command.getProductId(), command.getQuantity(), status));
    }

    @EventSourcingHandler
    public void on(StockCreatedEvent event) {
        this.stockId = event.getStockId();
        this.productId = event.getProductId();
        this.quantity = event.getQuantity();
        this.status = event.getStatus();
    }

    //Update Stock
    @CommandHandler
    public void handle(UpdateStockCommand command) {
        StockStatus newStatus = determineStockStatus(command.getQuantity());
        apply(new StockUpdatedEvent(command.getStockId(), command.getProductId(), command.getQuantity(), newStatus));
    }

    @EventSourcingHandler
    public void on(StockUpdatedEvent event) {
        this.quantity = event.getQuantity();
        this.status = event.getStatus();
    }

    //Delete Stock
    @CommandHandler
    public void handle(DeleteStockCommand command) {
        apply(new StockDeletedEvent(command.getStockId()));
    }

    @EventSourcingHandler
    public void on(StockDeletedEvent event) {
        // Nothing to retain after deletion
    }

    private StockStatus determineStockStatus(int quantity) {
        if (quantity == 0) {
            return StockStatus.OUT_OF_STOCK;
        } else if (quantity < 10) { // Threshold for low stock
            return StockStatus.LOW;
        } else {
            return StockStatus.AVAILABLE;
        }
    }
}

