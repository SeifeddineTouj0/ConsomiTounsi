package com.example.users.mappers;

import com.example.coreapi.users.queries.UserInfo;
import com.example.users.entities.User;

public class UserMapper {

    public static UserInfo toDTO(User user){
        return UserInfo.builder().
                id(user.getId()).
                username(user.getUsername()).
                email(user.getEmail()).
                role(user.getRole()).
                isActive(user.isActive()).
                createdAt(user.getCreatedAt()).
                build();
    }
}
