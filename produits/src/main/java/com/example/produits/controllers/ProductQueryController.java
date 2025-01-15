package com.example.produits.controllers;

import com.example.coreapi.produits.queries.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/products/queries")
public class ProductQueryController {

    private final QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }


    @GetMapping
    public CompletableFuture<ResponseEntity<List<ProductInfo>>> findAll(){

        return queryGateway.query(new ListAllProductsQuery(), ResponseTypes.multipleInstancesOf(ProductInfo.class))
                .thenApply(products -> ResponseEntity.ok(products));
    }
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ProductInfo>> findById(@PathVariable("id") String id) {
        return queryGateway.query(new FetchproductByIdQuery(id), ProductInfo.class)
                .thenApply(product -> ResponseEntity.ok(product));
    }

    @GetMapping("/isTunisian/{id}")
    public CompletableFuture<ResponseEntity<String>> checkIfTunisian(@PathVariable("id") String id){

        return queryGateway.query(new CheckForTunisianProductQuery(id),String.class).thenApply(response -> ResponseEntity.ok(response));
    }

    @GetMapping("/category/{category}")
    public CompletableFuture<ResponseEntity<List<ProductInfo>>> findByCategory(@PathVariable("category") String category){
        return queryGateway.query(new FetchProductByCategoryQuery(category), ResponseTypes.multipleInstancesOf(ProductInfo.class))
                .thenApply(products -> ResponseEntity.ok(products));
    }

}
