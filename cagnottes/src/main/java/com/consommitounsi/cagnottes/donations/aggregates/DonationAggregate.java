package com.consommitounsi.cagnottes.donations.aggregates;

import com.consommitounsi.cagnottes.donations.commands.AddProductToDonationCommand;
import com.consommitounsi.cagnottes.donations.commands.CreateDonationCommand;
import com.consommitounsi.cagnottes.donations.events.DonationCreatedEvent;
import com.consommitounsi.cagnottes.donations.events.ProductAddedToDonationEvent;
import com.example.coreapi.boutique.stock.queries.GetStockStatusQuery;
import com.example.coreapi.boutique.stock.responses.StockStatusResponse;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class DonationAggregate {

    @AggregateIdentifier
    private String donationId;
    private boolean isActive;
    private List<String> collectedProducts;
    private QueryGateway queryGateway;


//    private ProductServiceClient productServiceClient;

    public DonationAggregate() {
    }

    public DonationAggregate(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

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
    public void handle(AddProductToDonationCommand command) throws Exception {

        CompletableFuture<StockStatusResponse> stockStatus = queryGateway.query(new GetStockStatusQuery(command.getProductId()), ResponseTypes.instanceOf(StockStatusResponse.class));

        if (stockStatus.get().getQuantity() > 0) {
            // Apply event if product exists
            apply(new ProductAddedToDonationEvent(command.getProductId(), this.donationId));
        } else {
            // Handle the case where the product doesn't exist
            throw new Exception("Product with ID " + command.getProductId() + " not found.");
        }
    }

    @EventSourcingHandler
    public void on(ProductAddedToDonationEvent event) {
        this.collectedProducts.add(event.getProductId());
    }
}
