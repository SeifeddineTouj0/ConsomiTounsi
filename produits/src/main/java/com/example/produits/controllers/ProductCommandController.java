package com.example.produits.controllers;


import com.example.coreapi.produits.commands.CreateProductCommand;
import com.example.coreapi.produits.commands.DeleteProductCommand;
import com.example.coreapi.produits.commands.UpdateProductCommand;
import com.example.coreapi.produits.queries.FetchProductByNameOrBarCodeQuery;
import com.example.coreapi.produits.queries.ProductInfo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products/commands")
public class ProductCommandController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> addProduct(@RequestBody CreateProductCommand command) {

        return queryGateway.query(
                        new FetchProductByNameOrBarCodeQuery(command.getName(),command.getBarCode()), // Query for the product name
                        ProductInfo.class // Expected response type
                )
                .thenApply(productInfo -> {
                    if (productInfo != null) {
                        throw new IllegalArgumentException("A product with the same name or barcode already exists in the database");
                    }
                    // If no product exists, generate an ID and send the command
                    command.setId(UUID.randomUUID().toString());
                    commandGateway.sendAndWait(command);
                    return ResponseEntity.ok(command.getId());
                });
    }


    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteProduct(@PathVariable("id") String id){
        return commandGateway.sendAndWait(new DeleteProductCommand(id));
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<ProductInfo>> updateProduct(@RequestBody UpdateProductCommand updatedProduct){
        return commandGateway.sendAndWait(updatedProduct);
    }




}
