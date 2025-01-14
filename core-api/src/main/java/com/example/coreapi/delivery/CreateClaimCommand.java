package com.example.coreapi.delivery;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class CreateClaimCommand {
    @TargetAggregateIdentifier
    private final String claimId;
    private final ClaimDTO claimDTO;

    public CreateClaimCommand(String claimId, ClaimDTO claimDTO) {
        this.claimId = claimId;
        this.claimDTO = claimDTO;
    }
 }
