package tn.fst.igl5.delivery_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ClaimDeletedEvent {
    private final String claimId;

 }
