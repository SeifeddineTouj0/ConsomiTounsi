package com.example.coreapi.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class ClaimCreatedEvent {
    private final String claimId;
    private final ClaimDTO claimDTO;

}

