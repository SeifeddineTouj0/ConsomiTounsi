package com.consommitounsi.cagnottes.donations.aggregates;

import com.consommitounsi.cagnottes.donations.commands.AddProductToDonationCommand;
import com.consommitounsi.cagnottes.donations.commands.CreateDonationCommand;
import com.consommitounsi.cagnottes.donations.events.DonationCreatedEvent;
import com.consommitounsi.cagnottes.donations.events.ProductAddedToDonationEvent;
import com.example.coreapi.boutique.stock.queries.GetStockStatusQuery;
import com.example.coreapi.boutique.stock.responses.StockStatusResponse;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class DonationAggregate {

    @AggregateIdentifier
    private String donationId;
    private boolean isActive;
    private List<String> collectedProducts;

    @Autowired
    private QueryGateway queryGateway;

    @CommandHandler
    public DonationAggregate(CreateDonationCommand command) {
        apply(new DonationCreatedEvent(command.getDonationId(), true));
    }

    @EventSourcingHandler
    public void on(DonationCreatedEvent event) {
        this.donationId = event.getDonationId();
        this.isActive = true;
        this.collectedProducts = new ArrayList<>();
    }

    @CommandHandler
    public void DonationAggregate(AddProductToDonationCommand command, QueryGateway queryGateway) throws Exception {
        this.queryGateway = queryGateway;
        CompletableFuture<StockStatusResponse> stockStatus = queryGateway.query(
                new GetStockStatusQuery(command.getProductId()),
                ResponseTypes.instanceOf(StockStatusResponse.class)
        );

        try {
            StockStatusResponse response = stockStatus.get();
            if (response != null && response.getQuantity() > 0) {
                apply(new ProductAddedToDonationEvent(command.getProductId(), this.donationId));
            } else {
                throw new Exception("Product with ID " + command.getProductId() + " is out of stock.");
            }
        } catch (Exception e) {
            throw new Exception("Error checking product stock: " + e.getMessage());
        }
    }

    @EventSourcingHandler
    public void on(ProductAddedToDonationEvent event) {
        if (this.collectedProducts == null) {
            this.collectedProducts = new ArrayList<>();
        }
        this.collectedProducts.add(event.getProductId());
    }
}