package com.example.ventes.facture.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coreapi.ventes.facture.CreateFactureCommand;
import com.example.coreapi.ventes.facture.DeleteFactureCommand;
import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.facture.FactureInfoNamedQueries;
import com.example.coreapi.ventes.facture.TypeFacture;
import com.example.coreapi.ventes.facture.UpdateFactureCommand;

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

    @GetMapping("/{factureId}/pdf")
    public CompletableFuture<ResponseEntity<byte[]>> getFacturePdf(@RequestParam String factureId) {
        return queryGateway.query(FactureInfoNamedQueries.GENERATE_FACTURE_PDF, factureId, byte[].class)
                .thenApply(pdfContent -> {
                    if (pdfContent == null || pdfContent.length == 0) {
                        return ResponseEntity.notFound().build();
                    }
                    String fileName = "facture-" + factureId + ".pdf";
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(pdfContent);
                });
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

    @DeleteMapping("/{factureId}")
    public CompletableFuture<Void> deleteFacture(@RequestParam String factureId) {
        DeleteFactureCommand command = new DeleteFactureCommand(factureId);
        return commandGateway.send(command);
    }

    @PutMapping("/{factureId}")
    public CompletableFuture<String> updateFacture(@RequestParam String factureId, @RequestParam String paymentId,
            @RequestParam LocalDate dateFacture, @RequestParam TypeFacture typeFacture) {
        UpdateFactureCommand command = new UpdateFactureCommand(
                factureId,
                dateFacture,
                typeFacture,
                paymentId);

        CompletableFuture<String> commandResult = commandGateway.send(command);

        return commandResult;
    }

}
