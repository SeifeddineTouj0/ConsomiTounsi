package tn.fst.igl5.delivery_microservice.command.event;

import lombok.Getter;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;

@Getter
public class ClaimCreatedEvent {
    private final String claimId;
    private final ClaimDTO claimDTO;

    public ClaimCreatedEvent(String claimId, ClaimDTO claimDTO) {
        this.claimId = claimId;
        this.claimDTO = claimDTO;
    }
}

