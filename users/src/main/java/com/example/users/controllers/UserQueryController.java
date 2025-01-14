package com.example.users.controllers;


import com.example.coreapi.users.queries.ListAllUsersQuery;
import com.example.coreapi.users.queries.UserInfo;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
