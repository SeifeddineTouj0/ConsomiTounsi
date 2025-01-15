package com.example.coreapi.users.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class DeleteUserCommand {
    @TargetAggregateIdentifier
    String id;
}
