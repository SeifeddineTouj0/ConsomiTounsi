package com.example.boutiques.repositories;

import com.example.boutiques.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByProductId(String productId);
}
