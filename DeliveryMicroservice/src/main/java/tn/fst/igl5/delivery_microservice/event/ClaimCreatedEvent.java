package tn.fst.igl5.delivery_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;

@Getter
@AllArgsConstructor

public class ClaimCreatedEvent {
    private final String claimId;
    private final ClaimDTO claimDTO;

}

