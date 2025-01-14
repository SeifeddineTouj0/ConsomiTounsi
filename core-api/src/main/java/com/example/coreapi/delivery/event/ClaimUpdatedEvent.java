package com.example.coreapi.delivery.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.example.coreapi.delivery.ClaimDTO;

@Getter
@Setter
@AllArgsConstructor
public class ClaimUpdatedEvent {
    private final String claimId;
    private final ClaimDTO claimDTO;

 }
