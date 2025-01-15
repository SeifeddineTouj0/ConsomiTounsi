package com.example.users.aggregates;

import com.example.coreapi.users.commands.DeleteUserCommand;
import com.example.coreapi.users.commands.RegisterUserCommand;
import com.example.coreapi.users.commands.ReinstateUserCommand;
import com.example.coreapi.users.commands.UpdateUserDataCommand;
import com.example.coreapi.users.events.UserDeletedEvent;
import com.example.coreapi.users.events.UserRegisteredEvent;
import com.example.coreapi.users.events.UserReinstatedEvent;
import com.example.coreapi.users.events.UserUpdatedEvent;
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
        this.role = event.getRole();
        this.isActive = event.isActive();
        this.createdAt = event.getCreatedAt();
    }

    @CommandHandler
    public void handle(DeleteUserCommand command){
        apply(new UserDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent event){
        this.isActive=false ;
    }

    @CommandHandler
    public void handle(UpdateUserDataCommand command){
        apply(new UserUpdatedEvent(command.getId(), command.getUsername(), command.getEmail(), command.getPassword()));
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event){
        this.username=event.getUsername();
        this.email=event.getEmail();
        this.password= event.getPassword();
    }

    @CommandHandler
    public void handle(ReinstateUserCommand command){
        apply(new UserReinstatedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(UserReinstatedEvent event){
        this.isActive = true;
    }
}
