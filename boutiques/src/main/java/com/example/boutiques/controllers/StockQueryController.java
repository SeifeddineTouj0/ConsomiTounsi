package com.example.boutiques.controllers;

import com.example.boutiques.entities.Stock;
import com.example.coreapi.boutique.stock.queries.GetAllStocksQuery;
import com.example.coreapi.boutique.stock.queries.GetStockByIdQuery;
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
@RequestMapping("/api/stocks/queries")
public class StockQueryController {

    private final QueryGateway queryGateway;

    public StockQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public CompletableFuture<List<Stock>> getAllStocks() {
        return queryGateway.query(new GetAllStocksQuery(), ResponseTypes.multipleInstancesOf(Stock.class));

    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Stock>> getStockById(@PathVariable String id) {
        return queryGateway.query(new GetStockByIdQuery(id), Stock.class)
                .thenApply(stock -> stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build());
    }

}
