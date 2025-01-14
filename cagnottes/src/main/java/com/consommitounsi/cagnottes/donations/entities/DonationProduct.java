package com.consommitounsi.cagnottes.donations.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DonationProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // or another suitable strategy
    private Long id; // Surrogate primary key for each row

    @Column(nullable = false)
    private String productId; // The product ID being collected

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    @JsonBackReference // Prevent infinite recursion
    private Donation donation; // Reference to the parent donation

    // Constructors
    public DonationProduct() {
    }

    public DonationProduct(String productId, Donation donation) {
        this.productId = productId;
        this.donation = donation;
    }
}
