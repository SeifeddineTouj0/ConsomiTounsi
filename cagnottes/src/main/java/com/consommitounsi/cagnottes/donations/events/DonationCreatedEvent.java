package com.consommitounsi.cagnottes.donations.events;

import lombok.Getter;


@Getter
public class DonationCreatedEvent {

    private final String donationId;
    private final boolean isActive;

    public DonationCreatedEvent(String donationId, boolean isActive) {
        this.donationId = donationId;
        this.isActive = isActive;
    }

}
