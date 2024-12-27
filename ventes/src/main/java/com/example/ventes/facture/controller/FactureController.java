package com.example.ventes.facture.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coreapi.ventes.facture.CreateFactureCommand;
import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.facture.FactureInfoNamedQueries;
import com.example.coreapi.ventes.facture.TypeFacture;

@RestController
@RequestMapping("/factures")
public class FactureController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public FactureController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public CompletableFuture<String> createFacture(@RequestParam String paymentId, @RequestParam LocalDate dateFacture,
            @RequestParam TypeFacture typeFacture) {
        CreateFactureCommand command = new CreateFactureCommand(
                UUID.randomUUID().toString(),
                dateFacture,
                typeFacture,
                paymentId);

        CompletableFuture<String> commandResult = commandGateway.send(command);

        return commandResult;
    }

    @GetMapping
    public CompletableFuture<List<FactureInfo>> getFactures() {
        return queryGateway.query(FactureInfoNamedQueries.FIND_ALL, null,
                ResponseTypes.multipleInstancesOf(FactureInfo.class));
    }

    @GetMapping("/{factureId}")
    public CompletableFuture<FactureInfo> getFacture(@RequestParam String factureId) {
        return queryGateway.query(FactureInfoNamedQueries.FIND_ONE, factureId, FactureInfo.class);
    }

    @GetMapping("/user")
    public CompletableFuture<List<FactureInfo>> getFacturesByUser(@RequestParam String userId) {
        return queryGateway.query(FactureInfoNamedQueries.FIND_FACTURE_BY_USER, userId,
                ResponseTypes.multipleInstancesOf(FactureInfo.class));
    }

    @GetMapping("/type")
    public CompletableFuture<List<FactureInfo>> getFacturesByType(@RequestParam TypeFacture typeFacture) {
        return queryGateway.query(FactureInfoNamedQueries.FIND_FACTURE_BY_TYPE, typeFacture,
                ResponseTypes.multipleInstancesOf(FactureInfo.class));
    }

}
