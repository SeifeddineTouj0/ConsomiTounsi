package com.example.coreapi.users.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatedEvent {
    @TargetAggregateIdentifier
    private String id;
    private String username;
    private String email;
    private String password;
}
