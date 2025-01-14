package com.consommitounsi.cagnottes.cagnottes.controllers;

import com.consommitounsi.cagnottes.cagnottes.commands.AjouterDonCommand;
import com.consommitounsi.cagnottes.cagnottes.commands.CreerCagnotteCommand;
import com.consommitounsi.cagnottes.cagnottes.commands.SupprimerCagnotteCommand;
import com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte;
import com.consommitounsi.cagnottes.cagnottes.queries.GetAllCagnottesQuery;
import com.consommitounsi.cagnottes.cagnottes.queries.GetCagnotteByIdQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cagnottes")
public class CagnotteController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public CagnotteController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
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

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> supprimerCagnotte(@PathVariable String id) {
        return commandGateway.send(new SupprimerCagnotteCommand(id));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Cagnotte> getCagnotte(@PathVariable String id) {
        return queryGateway.query(new GetCagnotteByIdQuery(id), Cagnotte.class);
    }

    @GetMapping
    public CompletableFuture<List<Cagnotte>> getAllCagnottes() {
        return queryGateway.query(new GetAllCagnottesQuery(), ResponseTypes.multipleInstancesOf(Cagnotte.class));
    }
    
}
