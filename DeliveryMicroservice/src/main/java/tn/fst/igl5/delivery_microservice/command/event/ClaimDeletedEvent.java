package tn.fst.igl5.delivery_microservice.command.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimDeletedEvent {
    private final String claimId;

    public ClaimDeletedEvent(String claimId) {
        this.claimId = claimId;
    }    // Getters
 }
