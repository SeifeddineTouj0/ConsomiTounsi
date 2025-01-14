package tn.fst.igl5.delivery_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;

@Getter
@Setter
@AllArgsConstructor
public class ClaimUpdatedEvent {
    private final String claimId;
    private final ClaimDTO claimDTO;

 }
