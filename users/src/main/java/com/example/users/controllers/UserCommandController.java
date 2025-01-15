package com.example.users.controllers;

import com.example.coreapi.produits.queries.FetchproductByIdQuery;
import com.example.coreapi.users.commands.DeleteUserCommand;
import com.example.coreapi.users.commands.RegisterUserCommand;
import com.example.coreapi.users.commands.ReinstateUserCommand;
import com.example.coreapi.users.commands.UpdateUserDataCommand;
import com.example.coreapi.users.queries.FetchUserByIdQuery;
import com.example.coreapi.users.queries.FetchUserByNameOrEmailQuery;
import com.example.coreapi.users.queries.UserInfo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/users/commands")
public class UserCommandController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public UserCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> register(@RequestBody RegisterUserCommand command){

        return queryGateway.query(
                        new FetchUserByNameOrEmailQuery(command.getUsername(),command.getEmail()), // Query for the user
                        UserInfo.class // Expected response type
                )
                .thenApply(user -> {
                    if (user != null) {
                        throw new IllegalArgumentException("A user with the same username or email already exists in the database");
                    }
                    // If no product exists, generate an ID and send the command
                    command.setId(UUID.randomUUID().toString());
                    commandGateway.sendAndWait(command);
                    return ResponseEntity.ok(command.getId());
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteUser(@PathVariable("id") String id){
        return queryGateway.query(
                        new FetchUserByIdQuery(id), // Query for the user
                        UserInfo.class // Expected response type
                )
                .thenApply(user -> {
                    if (user == null) {
                        throw new IllegalArgumentException("No user with thus ID exists in the database");
                    }
                    return ResponseEntity.ok(commandGateway.sendAndWait(new DeleteUserCommand(id)));
                });
    }

    @PutMapping("/reinstate/{id}")
    public CompletableFuture<ResponseEntity<String>> reinstateUser(@PathVariable("id") String id){
        return queryGateway.query(
                        new FetchUserByIdQuery(id), // Query for the user
                        UserInfo.class // Expected response type
                )
                .thenApply(user -> {
                    if (user == null) {
                        throw new IllegalArgumentException("No user with thus ID exists in the database");
                    }
                    return ResponseEntity.ok(commandGateway.sendAndWait(new ReinstateUserCommand(id)));
                });
    }
    @PutMapping("/update")
    public CompletableFuture<ResponseEntity<UserInfo>> updateUser(@RequestBody UpdateUserDataCommand command){
        return queryGateway.query(
                        new FetchUserByIdQuery(command.getId()), // Query for the user
                        UserInfo.class // Expected response type
                )
                .thenApply(user -> {
                    if (user == null) {
                        throw new IllegalArgumentException("No user with thus ID exists in the database");
                    }
                    return ResponseEntity.ok(commandGateway.sendAndWait(command));
                });
    }


}
