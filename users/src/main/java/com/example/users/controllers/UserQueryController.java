package com.example.users.controllers;


import com.example.coreapi.users.queries.FetchUserByIdQuery;
import com.example.coreapi.users.queries.ListAllUsersQuery;
import com.example.coreapi.users.queries.UserInfo;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/users/queries")
public class UserQueryController {

    private final QueryGateway queryGateway;

    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }


    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserInfo>>> findAll() {
        // Send the query asynchronously
        return queryGateway.query(new ListAllUsersQuery(), ResponseTypes.multipleInstancesOf(UserInfo.class))
                .thenApply(users -> ResponseEntity.ok(users));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserInfo>> findById(@PathVariable("id") String id) {
        // Send the query asynchronously
        return queryGateway.query(new FetchUserByIdQuery(id), UserInfo.class)
                .thenApply(user -> ResponseEntity.ok(user));
    }

}
