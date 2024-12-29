package com.example.boutiques.controllers;

import com.example.coreapi.boutique.rayon.commands.CreateRayonCommand;
import com.example.coreapi.boutique.rayon.commands.DeleteRayonCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/rayons/commands")
public class RayonCommandController {

    private final CommandGateway commandGateway;

    public RayonCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<String> createRayon(@RequestBody CreateRayonCommand command) {
        command.setRayonId(UUID.randomUUID().toString());
        commandGateway.sendAndWait(command);
        return ResponseEntity.ok("Rayon created successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRayon(@PathVariable String id) {
        commandGateway.sendAndWait(new DeleteRayonCommand(id));
        return ResponseEntity.ok("Rayon deleted successfully!");
    }
}
