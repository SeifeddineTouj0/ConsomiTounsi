package tn.fst.igl5.delivery_microservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ClaimDeletedEvent {
    private final String claimId;

 }
