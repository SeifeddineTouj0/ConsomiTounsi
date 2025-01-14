package com.example.produits.controllers;

import com.example.coreapi.produits.queries.FetchproductByIdQuery;
import com.example.coreapi.produits.queries.ListAllProductsQuery;
import com.example.coreapi.produits.queries.ProductInfo;
import com.example.coreapi.users.queries.FetchUserByIdQuery;
import com.example.coreapi.users.queries.UserInfo;
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
                .thenApply(product -> ResponseEntity.ok(product));
    }
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ProductInfo>> findById(@PathVariable("id") String id) {
        return queryGateway.query(new FetchproductByIdQuery(id), ProductInfo.class)
                .thenApply(product -> ResponseEntity.ok(product));
    }

}
