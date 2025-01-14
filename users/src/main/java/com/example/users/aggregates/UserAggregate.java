package com.example.users.aggregates;

import com.example.coreapi.users.commands.RegisterUserCommand;
import com.example.coreapi.users.events.UserRegisteredEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;

    @CommandHandler
    public UserAggregate(RegisterUserCommand command){
        apply(new UserRegisteredEvent(command.getId(), command.getUsername(), command.getEmail(),command.getPassword(), command.getRole(), command.isActive(), command.getCreatedAt()));
    }


    @EventSourcingHandler
    public void on(UserRegisteredEvent event){
        this.id= event.getId();
        this.username=event.getUsername();
        this.email = event.getEmail();
        this.password= event.getPassword();
        this.isActive = event.isActive();
        this.createdAt = event.getCreatedAt();
    }
}
