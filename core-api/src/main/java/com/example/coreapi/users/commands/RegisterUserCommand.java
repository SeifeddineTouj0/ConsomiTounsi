package com.example.coreapi.users.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;
}
