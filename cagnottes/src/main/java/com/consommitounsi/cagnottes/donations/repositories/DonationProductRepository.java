package com.consommitounsi.cagnottes.donations.repositories;

import com.consommitounsi.cagnottes.donations.entities.DonationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationProductRepository extends JpaRepository<DonationProduct, Long> {
}
