package com.example.produits.projections;


import com.example.coreapi.produits.events.ProductCreatedEvent;
import com.example.coreapi.produits.events.ProductDeletedEvent;
import com.example.coreapi.produits.events.ProductUpdatedEvent;
import com.example.coreapi.produits.queries.FetchProductByNameOrBarCodeQuery;
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
                event.getBarCode(),
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

    @EventHandler
    public void on(ProductUpdatedEvent event){
        if(!produitRepository.findById(event.getId()).isPresent())
            throw new IllegalArgumentException("The product with this id ["+event.getId()+"] does not exist");

        Produit produit = produitRepository.findById(event.getId()).get();

        produit.setName(event.getName());
        produit.setCategory(event.getCategory());
        produit.setWeight(event.getWeight());
        produit.setBarCode(event.getBarCode());
        produit.setStorageType(event.getStorageType());
        produit.setPrice(event.getPrice());

        produitRepository.save(produit);

    }

    @EventHandler
    public String on(ProductDeletedEvent event){
        if(!produitRepository.findById(event.getId()).isPresent())
            throw new IllegalArgumentException("The product with this id ["+event.getId()+"] does not exist");
        else{
            produitRepository.delete(produitRepository.findById(event.getId()).get());
            return "Product Deleted From Database!";
        }
    }


    @QueryHandler
    public ProductInfo on(FetchProductByNameOrBarCodeQuery query){
        if(produitRepository.findByNameOrBarCode(query.getName(), query.getBarCode()).isPresent())
            return ProductMapper.toDTO(produitRepository.findByNameOrBarCode(query.getName(), query.getBarCode()).get());
        else
            return null;
    }
}
