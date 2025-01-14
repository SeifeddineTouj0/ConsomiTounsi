package com.consommitounsi.cagnottes.donations.queries;

public class GetDonationQuery {
    private final String donationId;

    public GetDonationQuery(String donationId) {
        this.donationId = donationId;
    }

    public String getDonationId() {
        return donationId;
    }
}
