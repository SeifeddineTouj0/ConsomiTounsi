package com.example.users.projections;


import com.example.coreapi.users.events.UserDeletedEvent;
import com.example.coreapi.users.events.UserRegisteredEvent;
import com.example.coreapi.users.events.UserReinstatedEvent;
import com.example.coreapi.users.events.UserUpdatedEvent;
import com.example.coreapi.users.queries.FetchUserByIdQuery;
import com.example.coreapi.users.queries.FetchUserByNameOrEmailQuery;
import com.example.coreapi.users.queries.ListAllUsersQuery;
import com.example.coreapi.users.queries.UserInfo;
import com.example.users.entities.User;
import com.example.users.mappers.UserMapper;
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
    private final UserRepository userRepository;

    public UserProjection(UserRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @EventHandler
    public void on(UserRegisteredEvent event){
        if(repository.findByUsername(event.getUsername()).isPresent()){
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
                        userInfoList.add(
                                new UserInfo(user.getId(),
                                        user.getUsername(),
                                        user.getEmail(),
                                        user.getRole(),
                                        user.isActive(),
                                        user.getCreatedAt()
                                ));
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

    @QueryHandler
    public UserInfo on(FetchUserByNameOrEmailQuery query){
        if(repository.findByUsernameOrEmail(query.getUsername(), query.getEmail()).isPresent()){
            return UserMapper.toDTO(repository.findByUsernameOrEmail(query.getUsername(), query.getEmail()).get());
        }
            return null;
    }

    @EventHandler
    public String on(UserDeletedEvent event){
        User user= userRepository.findById(event.getId()).get();
        if(user.isActive()){
            user.setActive(false);
            return "This Account with the ID ["+user.getId()+"] has been suspended";
        }else
            return "The Account is already Suspended !";

    }


    @EventHandler
    public String on(UserReinstatedEvent event){
        User user= userRepository.findById(event.getId()).get();
        if(!user.isActive()){
            user.setActive(true);
            return "This Account with the ID ["+user.getId()+"] has been reinstated";
        }else
            return "The Account is already Active !";
    }

    @EventHandler
    public UserInfo on(UserUpdatedEvent event){
        User user= userRepository.findById(event.getId()).get();
        user.setUsername(event.getUsername());
        user.setEmail(event.getEmail());
        user.setPassword(event.getPassword());
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }



}
