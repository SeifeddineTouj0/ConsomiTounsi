package com.example.produits.repository;

import com.example.produits.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProduitRepository extends JpaRepository<Produit,String> {

    Optional<Produit> findByName(String name);


    Optional<Produit> findByNameOrBarCode(String name, String barCode);

    List<Produit> findAllByCategory(String category);
}
