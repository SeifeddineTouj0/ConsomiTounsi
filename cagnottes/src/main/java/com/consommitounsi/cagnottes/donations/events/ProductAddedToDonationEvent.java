package com.consommitounsi.cagnottes.donations.events;

import lombok.Getter;

@Getter
public class ProductAddedToDonationEvent {
    private final String productId;
    private final String donationId;

    public ProductAddedToDonationEvent(String productId, String donationId) {
        this.productId = productId;
        this.donationId = donationId;
    }

}