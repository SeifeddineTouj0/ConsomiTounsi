package com.consommitounsi.cagnottes.donations.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class CreateDonationCommand {

    @TargetAggregateIdentifier
    private final String donationId;
    private final boolean isActive;

    public CreateDonationCommand(String donationId, boolean isActive) {
        this.donationId = donationId;
        this.isActive = isActive;
    }

}
