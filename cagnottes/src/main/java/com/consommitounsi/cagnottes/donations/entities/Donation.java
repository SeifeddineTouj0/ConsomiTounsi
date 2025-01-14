package com.consommitounsi.cagnottes.donations.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Donation {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String donationId; // Primary key for the donation entity

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Prevent infinite recursion
    private List<DonationProduct> collectedProducts; // List of related products

    @Column(nullable = false)
    private boolean isActive; // Status of the donation (e.g., whether it’s still open)


}
