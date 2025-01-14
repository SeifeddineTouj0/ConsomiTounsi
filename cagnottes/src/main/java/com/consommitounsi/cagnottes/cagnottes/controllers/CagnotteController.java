package com.consommitounsi.cagnottes.cagnottes.controllers;

import com.consommitounsi.cagnottes.cagnottes.commands.AjouterDonCommand;
import com.consommitounsi.cagnottes.cagnottes.commands.CreerCagnotteCommand;
import com.consommitounsi.cagnottes.cagnottes.commands.SupprimerCagnotteCommand;
import com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte;
import com.consommitounsi.cagnottes.cagnottes.queries.GetAllCagnottesQuery;
import com.consommitounsi.cagnottes.cagnottes.queries.GetCagnotteByIdQuery;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cagnottes")
public class CagnotteController {
    private static final Logger logger = LoggerFactory.getLogger(CagnotteController.class);
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public CagnotteController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    @Retry(name = "default", fallbackMethod = "creerCagnotteFallback")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "creerCagnotteFallback")
    public CompletableFuture<String> creerCagnotte(@RequestBody CreerCagnotteCommand command) {
        return commandGateway.send(command);
    }

    public CompletableFuture<String> creerCagnotteFallback(CreerCagnotteCommand command, Throwable throwable) {
        logger.error("Error creating cagnotte: {}", throwable.getMessage());
        return CompletableFuture.completedFuture("Cagnotte creation failed, please try again later.");
    }

    @PostMapping("/{id}/dons")
    @Retry(name = "default", fallbackMethod = "ajouterDonFallback")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "ajouterDonFallback")
    public CompletableFuture<Void> ajouterDon(@PathVariable String id, @RequestBody AjouterDonCommand command) {
        command = new AjouterDonCommand(id, command.getMontant());
        return commandGateway.send(command);
    }

    public CompletableFuture<Void> ajouterDonFallback(AjouterDonCommand command, Throwable throwable) {
        logger.error("Error adding donation: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(null);
    }

    @DeleteMapping("/{id}")
    @Retry(name = "default", fallbackMethod = "supprimerCagnotteFallback")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "supprimerCagnotteFallback")
    public CompletableFuture<Void> supprimerCagnotte(@PathVariable String id) {
        return commandGateway.send(new SupprimerCagnotteCommand(id));
    }

    public CompletableFuture<Void> supprimerCagnotteFallback(String id, Throwable throwable) {
        logger.error("Error deleting cagnotte: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(null);
    }

    @GetMapping("/{id}")
    @Retry(name = "default", fallbackMethod = "getCagnotteFallback")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "getCagnotteFallback")
    public CompletableFuture<Cagnotte> getCagnotte(@PathVariable String id) {
        return queryGateway.query(new GetCagnotteByIdQuery(id), Cagnotte.class);
    }

    public CompletableFuture<Cagnotte> getCagnotteFallback(String id, Throwable throwable) {
        logger.error("Error getting cagnotte: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(new Cagnotte());
    }

    @GetMapping
    @Retry(name = "default", fallbackMethod = "getAllCagnottesFallback")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "getAllCagnottesFallback")
    public CompletableFuture<List<Cagnotte>> getAllCagnottes() {
        return queryGateway.query(new GetAllCagnottesQuery(), ResponseTypes.multipleInstancesOf(Cagnotte.class));
    }

    public CompletableFuture<List<Cagnotte>> getAllCagnottesFallback(Throwable throwable) {
        logger.error("Error getting all cagnottes: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(List.of(new Cagnotte()));
    }
}
