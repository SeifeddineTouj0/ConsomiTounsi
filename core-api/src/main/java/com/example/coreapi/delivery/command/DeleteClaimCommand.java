package com.example.coreapi.delivery.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class DeleteClaimCommand {
    @TargetAggregateIdentifier
    private final String claimId;

    public DeleteClaimCommand(String claimId) {
        this.claimId = claimId;
    }    // Getters
 }
