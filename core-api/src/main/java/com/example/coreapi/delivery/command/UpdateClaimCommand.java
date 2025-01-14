package com.example.coreapi.delivery.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.example.coreapi.delivery.ClaimDTO;

@Getter
@Setter
public class UpdateClaimCommand {
    @TargetAggregateIdentifier
    private final String claimId;
    private final ClaimDTO claimDTO;

    public UpdateClaimCommand(String claimId, ClaimDTO claimDTO) {
        this.claimId = claimId;
        this.claimDTO = claimDTO;
    }
    //
}
