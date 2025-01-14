package com.example.produits.controllers;


import com.example.coreapi.produits.commands.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products/commands")
public class ProductCommandController {

    private final CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> addProduct(@RequestBody CreateProductCommand command){
        command.setId(UUID.randomUUID().toString());

        return CompletableFuture.supplyAsync(() -> {
            commandGateway.sendAndWait(command);
            return ResponseEntity.ok(command.getId());
        });
    }


}
