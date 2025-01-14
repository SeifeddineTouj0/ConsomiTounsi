package com.example.users.projections;


import com.example.coreapi.users.events.UserRegisteredEvent;
import com.example.coreapi.users.queries.ListAllUsersQuery;
import com.example.coreapi.users.queries.UserInfo;
import com.example.users.repositories.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProjection {

    private final UserRepository repository;

    @Autowired
    public UserProjection(UserRepository repository) {
        this.repository = repository;
    }


    @EventHandler
    public void on(UserRegisteredEvent event){
        if(repository.findByUsername(event.getUsername()).isPresent())
            throw new IllegalArgumentException("This Username is already taken!");
        if(repository.findByEmail(event.getEmail()).isPresent())
            throw new IllegalArgumentException("An account with this email is already registered!");

        repository.save(UserInfo.
                builder().
                id(event.getId()).
                username(event.getUsername()).
                email(event.getEmail()).
                password(event.getPassword()).
                isActive(event.isActive()).
                createdAt(event.getCreatedAt()).
                build());
    }

    @QueryHandler
    public List<UserInfo> handle(ListAllUsersQuery query){
        return repository.findAll();
    }


}
