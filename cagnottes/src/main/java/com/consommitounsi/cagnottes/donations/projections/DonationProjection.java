package com.consommitounsi.cagnottes.donations.projections;

import com.consommitounsi.cagnottes.donations.dto.DonationResponse;
import com.consommitounsi.cagnottes.donations.entities.Donation;
import com.consommitounsi.cagnottes.donations.entities.DonationProduct;
import com.consommitounsi.cagnottes.donations.events.DonationCreatedEvent;
import com.consommitounsi.cagnottes.donations.events.ProductAddedToDonationEvent;
import com.consommitounsi.cagnottes.donations.queries.GetAllDonationsQuery;
import com.consommitounsi.cagnottes.donations.queries.GetDonationQuery;
import com.consommitounsi.cagnottes.donations.repositories.DonationProductRepository;
import com.consommitounsi.cagnottes.donations.repositories.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DonationProjection {

    private final DonationRepository donationRepository;
    private final DonationProductRepository donationProductRepository;

    @EventHandler
    @Transactional
    public void on(DonationCreatedEvent event) {
        Donation donation = new Donation();
        donation.setActive(event.isActive());
        donation.setDonationId(event.getDonationId());
        donationRepository.save(donation); // Persist the new donation in the query database
    }

    @EventHandler
    @Transactional
    public void on(ProductAddedToDonationEvent event) {
        Donation donation = donationRepository.findByDonationIdWithProducts(event.getDonationId());
        if (donation == null) {
            return;
        }
        DonationProduct donationProduct = new DonationProduct();
        donationProduct.setProductId(event.getProductId());
        System.err.println("EVENT PRODUCTID==" + event.getProductId());
        donationProduct.setDonation(donation);

        donationProductRepository.save(donationProduct); // Persist the product in the query database
    }

    @QueryHandler
    @Transactional
    public List<DonationResponse> handle(GetAllDonationsQuery query) {
        // Retrieve all donations from the database
        List<Donation> donations = donationRepository.findAll();

        // Map each Donation entity to a DonationResponse DTO
        return donations.stream()
                .map(donation -> new DonationResponse(
                        donation.getDonationId(),
                        donation.isActive(),
                        donation.getCollectedProducts()
                ))
                .collect(Collectors.toList());
    }

    @QueryHandler
    @Transactional
    public DonationResponse handle(GetDonationQuery query) {
        Donation donation = donationRepository.findByDonationId(query.getDonationId());
        if (donation == null) {
            return null;
        }

        return new DonationResponse(
                donation.getDonationId(),
                donation.isActive(),
                donation.getCollectedProducts()
        );
    }
}
