package com.example.coreapi.users.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UserReinstatedEvent {
    @TargetAggregateIdentifier
    String id;
}
