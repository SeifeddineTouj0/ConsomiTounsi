package com.consommitounsi.cagnottes.cagnottes.controllers;

import com.consommitounsi.cagnottes.cagnottes.commands.AjouterDonCommand;
import com.consommitounsi.cagnottes.cagnottes.commands.CreerCagnotteCommand;
import com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte;
import com.consommitounsi.cagnottes.cagnottes.projections.CagnotteProjection;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cagnottes")
public class CagnotteController {
    private final CommandGateway commandGateway;
    private final CagnotteProjection projection;

    public CagnotteController(CommandGateway commandGateway, CagnotteProjection projection) {
        this.commandGateway = commandGateway;
        this.projection = projection;
    }

    @PostMapping
    public CompletableFuture<String> creerCagnotte(@RequestBody CreerCagnotteCommand command) {
        return commandGateway.send(command);
    }

    @PostMapping("/{id}/dons")
    public CompletableFuture<Void> ajouterDon(@PathVariable String id, @RequestBody AjouterDonCommand command) {
        command = new AjouterDonCommand(id, command.getMontant());
        return commandGateway.send(command);
    }

    @GetMapping("/{id}")
    public Cagnotte getCagnotte(@PathVariable String id) {
        return projection.getCagnotte(id);
    }
}
