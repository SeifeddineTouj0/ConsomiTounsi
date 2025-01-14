package com.consommitounsi.cagnottes.donations.aggregates;

import com.consommitounsi.cagnottes.donations.commands.AddProductToDonationCommand;
import com.consommitounsi.cagnottes.donations.commands.CreateDonationCommand;
import com.consommitounsi.cagnottes.donations.events.DonationCreatedEvent;
import com.consommitounsi.cagnottes.donations.events.ProductAddedToDonationEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class DonationAggregate {

    @AggregateIdentifier
    private String donationId;
    private boolean isActive;
    private List<String> collectedProducts;


//    private ProductServiceClient productServiceClient;

    public DonationAggregate() {
    }

//    public DonationAggregate(ProductServiceClient productServiceClient) {
//        this.productServiceClient = productServiceClient;
//    }

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
        // Check if the product exists using OpenFeign
//        boolean productExists = productServiceClient.checkProductExists(command.getProductId());
        boolean productExists = true;
        if (productExists) {
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
