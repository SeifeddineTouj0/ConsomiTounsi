package com.consommitounsi.cagnottes.donations.controllers;

import com.consommitounsi.cagnottes.donations.commands.AddProductToDonationCommand;
import com.consommitounsi.cagnottes.donations.commands.CreateDonationCommand;
import com.consommitounsi.cagnottes.donations.dto.DonationResponse;
import com.consommitounsi.cagnottes.donations.entities.Donation;
import com.consommitounsi.cagnottes.donations.queries.GetAllDonationsQuery;
import com.consommitounsi.cagnottes.donations.queries.GetDonationQuery;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public DonationController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Retry(name = "donationRetry", fallbackMethod = "createDonationFallback")
    @CircuitBreaker(name = "donationCircuitBreaker", fallbackMethod = "createDonationFallback")
    @RateLimiter(name = "donationRateLimiter", fallbackMethod = "createDonationFallback")
    @PostMapping()
    public ResponseEntity<String> createDonation(@RequestBody Donation donation) {
        try {
            CreateDonationCommand command = new CreateDonationCommand(donation.getDonationId(), donation.isActive());
            commandGateway.sendAndWait(command);
            logger.info("Donation created successfully with ID: {}", command.getDonationId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Donation created successfully with ID: " + command.getDonationId());
        } catch (Exception e) {
            logger.error("Error creating donation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating donation: " + e.getMessage());
        }
    }

    public ResponseEntity<String> createDonationFallback(Donation donation, Throwable throwable) {
        logger.error("Fallback triggered for createDonation: {}", throwable.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallback: Error creating donation.");
    }

    @Retry(name = "productRetry", fallbackMethod = "collectProductFallback")
    @CircuitBreaker(name = "productCircuitBreaker", fallbackMethod = "collectProductFallback")
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "collectProductFallback")
    @PostMapping("/{donationId}/add-product")
    public ResponseEntity<String> collectProduct(@PathVariable String donationId, @RequestBody AddProductToDonationCommand command) {
        AddProductToDonationCommand collectCommand = new AddProductToDonationCommand(donationId, command.getProductId());

        try {
            commandGateway.sendAndWait(collectCommand);
            logger.info("Product {} collected successfully for donation ID: {}", command.getProductId(), donationId);
            return ResponseEntity.ok("Product " + command.getProductId() + " collected successfully for donation ID: " + donationId);
        } catch (Exception e) {
            logger.error("Error collecting product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error collecting product: " + e.getMessage());
        }
    }

    public ResponseEntity<String> collectProductFallback(String donationId, AddProductToDonationCommand command, Throwable throwable) {
        logger.error("Fallback triggered for collectProduct: {}", throwable.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallback: Error collecting product.");
    }

    @Retry(name = "getAllDonationsRetry", fallbackMethod = "getAllDonationsFallback")
    @CircuitBreaker(name = "getAllDonationsCircuitBreaker", fallbackMethod = "getAllDonationsFallback")
    @RateLimiter(name = "getAllDonationsRateLimiter", fallbackMethod = "getAllDonationsFallback")
    @GetMapping
    public CompletableFuture<List<DonationResponse>> getAllDonations() {
        return queryGateway.query(
                new GetAllDonationsQuery(),
                ResponseTypes.multipleInstancesOf(DonationResponse.class)
        );
    }

    public CompletableFuture<List<DonationResponse>> getAllDonationsFallback(Throwable throwable) {
        logger.error("Fallback triggered for getAllDonations: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(List.of());  // Returning an empty list as fallback
    }

    @Retry(name = "getDonationRetry", fallbackMethod = "getDonationFallback")
    @CircuitBreaker(name = "getDonationCircuitBreaker", fallbackMethod = "getDonationFallback")
    @RateLimiter(name = "getDonationRateLimiter", fallbackMethod = "getDonationFallback")
    @GetMapping("/{donationId}")
    public CompletableFuture<DonationResponse> getDonation(@PathVariable String donationId) {
        return queryGateway.query(
                new GetDonationQuery(donationId),
                ResponseTypes.instanceOf(DonationResponse.class)
        );
    }

    public CompletableFuture<DonationResponse> getDonationFallback(String donationId, Throwable throwable) {
        logger.error("Fallback triggered for getDonation with ID {}: {}", donationId, throwable.getMessage());
        DonationResponse fallbackResponse = new DonationResponse(donationId, "Fallback Donation", false, 0);
        return CompletableFuture.completedFuture(fallbackResponse);  // Returning a fallback response
    }
}
