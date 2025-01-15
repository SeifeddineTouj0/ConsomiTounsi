package com.example.boutiques.projections;

import com.example.boutiques.entities.Stock;
import com.example.boutiques.repositories.StockRepository;
import com.example.coreapi.boutique.stock.queries.GetAllStocksQuery;
import com.example.coreapi.boutique.stock.queries.GetStockByIdQuery;
import com.example.coreapi.boutique.stock.queries.GetStockStatusQuery;
import com.example.coreapi.boutique.stock.responses.StockStatusResponse;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StockProjection {
    @Autowired
    private StockRepository stockRepository;


    @QueryHandler
    public List<Stock> handle(GetAllStocksQuery query) {
        return stockRepository.findAll();
    }

    @QueryHandler
    public Stock handle(GetStockByIdQuery query) {
        return stockRepository.findById(query.getStockId()).orElse(null);
    }


    @QueryHandler
    public StockStatusResponse handle(GetStockStatusQuery query) {
        Optional<Stock> stock = stockRepository.findByProductId(query.getProductId());
        return stock.map(value -> new StockStatusResponse(value.getId(), value.getStatus(), value.getQuantity()))
                .orElse(null);
    }
}
