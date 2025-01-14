package com.example.users.config;

import com.example.coreapi.users.queries.ListAllUsersQuery;
import com.example.coreapi.users.queries.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class JacksonConfig {

//    @Bean
//    public ObjectMapper axonObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerSubtypes(ListAllUsersQuery.class, UserInfo.class);
//        return objectMapper;
//    }
}