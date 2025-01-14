package com.consommitounsi.cagnottes.donations.controllers;

import com.consommitounsi.cagnottes.donations.commands.AddProductToDonationCommand;
import com.consommitounsi.cagnottes.donations.commands.CreateDonationCommand;
import com.consommitounsi.cagnottes.donations.dto.DonationResponse;
import com.consommitounsi.cagnottes.donations.entities.Donation;
import com.consommitounsi.cagnottes.donations.queries.GetAllDonationsQuery;
import com.consommitounsi.cagnottes.donations.queries.GetDonationQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final CommandGateway commandGateway; // Inject Axon's CommandGateway for sending commands
    private final QueryGateway queryGateway;

    @Autowired
    public DonationController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping()
    public ResponseEntity<String> createDonation(@RequestBody Donation donation) {
        try {
            // Sending the CreateDonationCommand to Axon
            CreateDonationCommand command = new CreateDonationCommand(donation.getDonationId(), donation.isActive());
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.CREATED).body("Donation created successfully with ID: " + command.getDonationId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating donation: " + e.getMessage());
        }
    }

    @PostMapping("/{donationId}/add-product")
    public ResponseEntity<String> collectProduct(
            @PathVariable String donationId, @RequestBody AddProductToDonationCommand command) {

        // Adjust the command with the specific donationId
        AddProductToDonationCommand collectCommand = new AddProductToDonationCommand(donationId, command.getProductId());

        try {
            // Sending the CollectProductCommand to Axon
            commandGateway.sendAndWait(collectCommand);
            return ResponseEntity.ok("Product " + command.getProductId() + " collected successfully for donation ID: " + donationId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error collecting product: " + e.getMessage());
        }
    }

    @GetMapping
    public CompletableFuture<List<DonationResponse>> getAllDonations() {
        return queryGateway.query(
                new GetAllDonationsQuery(),
                ResponseTypes.multipleInstancesOf(DonationResponse.class)
        );
    }

    @GetMapping("/{donationId}")
    public CompletableFuture<DonationResponse> getDonation(@PathVariable String donationId) {
        return queryGateway.query(
                new GetDonationQuery(donationId),
                ResponseTypes.instanceOf(DonationResponse.class)
        );
    }
}

