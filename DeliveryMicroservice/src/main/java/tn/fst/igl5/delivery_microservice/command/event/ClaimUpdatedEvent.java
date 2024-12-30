package tn.fst.igl5.delivery_microservice.command.event;

import lombok.Getter;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;

@Getter
@Setter
public class ClaimUpdatedEvent {
    private final String claimId;
    private final ClaimDTO claimDTO;

    public ClaimUpdatedEvent(String claimId, ClaimDTO claimDTO) {
        this.claimId = claimId;
        this.claimDTO = claimDTO;
    }    // Getters
 }
