package com.example.users.controllers;


import com.example.coreapi.users.commands.RegisterUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/users/commands")
public class UserCommandController {

    private final CommandGateway commandGateway;

    public UserCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> register(@RequestBody RegisterUserCommand command){
        command.setId(UUID.randomUUID().toString());

        // Send the command asynchronously
        return CompletableFuture.supplyAsync(() -> {
            commandGateway.sendAndWait(command);
            return ResponseEntity.ok(command.getId());
        });
    }


}
