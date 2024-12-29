package com.example.boutiques.handlers;

import com.example.boutiques.entities.Stock;
import com.example.boutiques.repositories.StockRepository;
import com.example.coreapi.boutique.stock.events.StockCreatedEvent;
import com.example.coreapi.boutique.stock.events.StockDeletedEvent;
import com.example.coreapi.boutique.stock.events.StockUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockEventHandler {

    private final StockRepository stockRepository;

    @Autowired
    public StockEventHandler(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @EventHandler
    public void on(StockCreatedEvent event) {
        Stock stock = new Stock();
        stock.setProductId(event.getProductId());
        stock.setQuantity(event.getQuantity());
        stock.setStatus(event.getStatus());

        stockRepository.save(stock);
        System.out.println("Stock created and saved to DB: Product ID " + stock.getProductId());
    }

    @EventHandler
    public void on(StockUpdatedEvent event) {
        Optional<Stock> optionalStock = stockRepository.findById(event.getStockId());
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setQuantity(event.getQuantity());
            stock.setStatus(event.getStatus());

            stockRepository.save(stock);
            System.out.println("Stock updated in DB: Stock ID " + stock.getId());
        } else {
            System.err.println("Stock with ID " + event.getStockId() + " not found for update.");
        }
    }

    @EventHandler
    public void on(StockDeletedEvent event) {
        stockRepository.deleteById(event.getStockId());
        System.out.println("Stock deleted from DB: Stock ID " + event.getStockId());
    }
}