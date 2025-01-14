package com.consommitounsi.cagnottes.donations.repositories;

import com.consommitounsi.cagnottes.donations.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {
    Donation findByDonationId(String donationId);

    @Query("SELECT d FROM Donation d LEFT JOIN FETCH d.collectedProducts WHERE d.donationId = :donationId")
    Donation findByDonationIdWithProducts(@Param("donationId") String donationId);

}
