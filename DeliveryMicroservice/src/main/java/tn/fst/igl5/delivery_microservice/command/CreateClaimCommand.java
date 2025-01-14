package tn.fst.igl5.delivery_microservice.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;

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
