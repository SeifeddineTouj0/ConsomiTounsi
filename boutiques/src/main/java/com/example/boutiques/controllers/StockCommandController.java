package com.example.boutiques.controllers;

import com.example.coreapi.boutique.stock.commands.CreateStockCommand;
import com.example.coreapi.boutique.stock.commands.DeleteStockCommand;
import com.example.coreapi.boutique.stock.commands.UpdateStockCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks/commands")
public class StockCommandController {

    private final CommandGateway commandGateway;

    public StockCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<String> createStock(@RequestBody CreateStockCommand command) {
        commandGateway.sendAndWait(command);
        return ResponseEntity.ok("Stock created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStock(@PathVariable String id, @RequestBody UpdateStockCommand command) {
        command.setStockId(id);
        commandGateway.sendAndWait(command);
        return ResponseEntity.ok("Stock updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable String id) {
        commandGateway.sendAndWait(new DeleteStockCommand(id));
        return ResponseEntity.ok("Stock deleted successfully!");
    }
}