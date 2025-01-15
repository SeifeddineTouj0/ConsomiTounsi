package com.example.produits.projections;


import com.example.coreapi.produits.events.ProductCreatedEvent;
import com.example.coreapi.produits.events.ProductDeletedEvent;
import com.example.coreapi.produits.events.ProductUpdatedEvent;
import com.example.coreapi.produits.queries.*;
import com.example.produits.entities.Produit;
import com.example.produits.mappers.ProductMapper;
import com.example.produits.repository.ProduitRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if(produitRepository.findById(query.getId()).isPresent())
            return ProductMapper.toDTO(produitRepository.findById(query.getId()).get());
        else
            return null;
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

    @QueryHandler
    public String on(CheckForTunisianProductQuery query) {
        // Check if the product exists
        Produit produit = produitRepository.findById(query.getId())
                .orElseThrow(() -> new IllegalArgumentException("The product with this id [" + query.getId() + "] does not exist"));

        // Check if the barcode starts with "619"
        if (produit.getBarCode().startsWith("619")) {
            return "This Product Was Made In Tunisia! [BARCODE:" + produit.getBarCode() + "]";
        } else {
            return "This Product Was NOT Made In Tunisia! [BARCODE:" + produit.getBarCode() + "]";
        }
    }

    @QueryHandler
    public List<ProductInfo> on(FetchProductByCategoryQuery query){

        List<Produit> produits = produitRepository.findAllByCategory(query.getCategory());

        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

        produits.forEach(produit -> {
            productInfoList.add(ProductMapper.toDTO(produit));
        });


        return productInfoList;
    }

    @QueryHandler
    public List<ProductInfo> on(FetchFilteredProductsQuery query) {
        List<Produit> products = produitRepository.findAll();
        List<Produit> filteredProducts = products.stream()
                .filter(product -> query.getCategory() == null || product.getCategory().equals(query.getCategory()))
                .filter(product -> query.getStorageType() == null || product.getStorageType().equals(query.getStorageType()))
                .filter(product -> query.getMinWeight() == null || product.getWeight() >= query.getMinWeight())
                .filter(product -> query.getMaxWeight() == null || product.getWeight() <= query.getMaxWeight())
                .filter(product -> query.getMinPrice() == null || product.getPrice() >= query.getMinPrice())
                .filter(product -> query.getMaxPrice() == null || product.getPrice() <= query.getMaxPrice())
                .toList();

        List<ProductInfo> filteredProductInfo = new ArrayList<ProductInfo>();
        filteredProducts.forEach(produit -> {
            filteredProductInfo.add(ProductMapper.toDTO(produit));
        });

        return filteredProductInfo ;
    }



}
