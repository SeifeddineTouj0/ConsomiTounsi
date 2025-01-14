package com.consommitounsi.cagnottes.donations.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class AddProductToDonationCommand {

    @TargetAggregateIdentifier
    private final String donationId;
    private final String productId;

    public AddProductToDonationCommand(String donationId, String productId) {
        this.donationId = donationId;
        this.productId = productId;
    }

}

