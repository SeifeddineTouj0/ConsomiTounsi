package com.example.users.projections;


import com.example.coreapi.users.events.UserRegisteredEvent;
import com.example.coreapi.users.queries.FetchUserByIdQuery;
import com.example.coreapi.users.queries.ListAllUsersQuery;
import com.example.coreapi.users.queries.UserInfo;
import com.example.users.entities.User;
import com.example.users.repositories.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserProjection {

    private final UserRepository repository;

    public UserProjection(UserRepository repository) {
        this.repository = repository;
    }


    @EventHandler
    public void on(UserRegisteredEvent event){
        if(repository.findByUsername(event.getUsername()).isPresent()){
            System.out.println("    AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            throw new IllegalArgumentException("This Username is already taken!");
        }
        if(repository.findByEmail(event.getEmail()).isPresent())
            throw new IllegalArgumentException("An account with this email is already registered!");
         User user =  new User(event.getId(), event.getUsername(), event.getEmail(), event.getPassword(), event.getRole(), event.isActive(), event.getCreatedAt());
        repository.save(user);
    }

    @QueryHandler
    public List<UserInfo> handle(ListAllUsersQuery query){

            List<User> users =repository.findAll() ;
            List<UserInfo> userInfoList = new ArrayList<UserInfo>();

            users.forEach(user -> {
                userInfoList.add(UserInfo.builder().id(user.getId()).
                        username(user.getUsername()).
                        email(user.getEmail()).
                        isActive(user.isActive()).
                        createdAt(user.getCreatedAt()).
                        role(user.getRole()).
                        build());
            });
        return userInfoList;
    }

    @QueryHandler
    public UserInfo handle(FetchUserByIdQuery query){

        User user =repository.findById(query.getId()).get() ;


        return UserInfo.builder().id(user.getId()).
                username(user.getUsername()).
                email(user.getEmail()).
                isActive(user.isActive()).
                role(user.getRole()).
                createdAt(user.getCreatedAt()).
                build();
    }


}
