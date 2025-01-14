package com.consommitounsi.cagnottes.donations.dto;


import com.consommitounsi.cagnottes.donations.entities.DonationProduct;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class DonationResponse {
    private String donationId;
    private boolean active;
    private List<DonationProduct> collectedProducts;


    public DonationResponse(String donationId, boolean active, List<DonationProduct> collectedProducts) {
        this.donationId = donationId;
        this.active = active;
        this.collectedProducts = collectedProducts;
    }

}

