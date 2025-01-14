package com.example.produits.projections;


import com.example.coreapi.produits.events.ProductCreatedEvent;
import com.example.coreapi.produits.queries.FetchproductByIdQuery;
import com.example.coreapi.produits.queries.ListAllProductsQuery;
import com.example.coreapi.produits.queries.ProductInfo;
import com.example.produits.entities.Produit;
import com.example.produits.mappers.ProductMapper;
import com.example.produits.repository.ProduitRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProduitProjections {
    private final ProduitRepository produitRepository;


    public ProduitProjections(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        if(produitRepository.findByName(event.getName()).isPresent())
            throw new IllegalArgumentException("A product with the same name already exists in the database");

        Produit produit = new Produit(
                event.getId(),
                event.getName(),
                event.getCategory(),
                event.getStorageType(),
                event.getWeight(),
                event.getPrice()
        );
        produitRepository.save(produit);
    }

    @QueryHandler
    public ProductInfo on(FetchproductByIdQuery query){
        return ProductMapper.toDTO(produitRepository.findById(query.getId()).get());
    }


    @QueryHandler
    public List<ProductInfo> on(ListAllProductsQuery query){
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

        produitRepository.findAll().forEach(produit -> productInfoList.add(ProductMapper.toDTO(produit)));

        return productInfoList;
    }
}
