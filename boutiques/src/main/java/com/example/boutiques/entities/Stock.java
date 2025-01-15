package com.example.boutiques.entities;

import com.example.coreapi.boutique.stock.enumerations.StockStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks")
public class Stock {
    @Id
    private String id;

    @Column(nullable = false)
    @Unique
    private String productId; // ID of the product

    @Column(nullable = false)
    private int quantity; // Quantity in stock

    @Column(nullable = false)
    private StockStatus status;
}
