package com.example.ventes.payment.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coreapi.ventes.payment.CreatePaymentCommand;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.PaymentInfoNamedQueries;
import com.example.coreapi.ventes.payment.StatusPaiment;
import com.example.coreapi.ventes.payment.TypePayment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public PaymentController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping()
    public CompletableFuture<String> createPayment(@RequestParam TypePayment typePayment,
            @RequestParam Double montant, @RequestParam LocalDateTime datePayment,
            @RequestParam StatusPaiment statusPayment,
            @RequestParam String userId, @RequestParam Set<String> produitIds) {

        CreatePaymentCommand command = new CreatePaymentCommand(UUID.randomUUID().toString(), typePayment, montant,
                datePayment, statusPayment, userId, produitIds);

        CompletableFuture<String> commandResult = commandGateway.send(command);

        return commandResult;
    }

    @GetMapping
    public CompletableFuture<List<PaymentInfo>> findAll() {
        return queryGateway.query(
                PaymentInfoNamedQueries.FIND_ALL,
                null,
                ResponseTypes.multipleInstancesOf(PaymentInfo.class));
    }
}
