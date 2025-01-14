package com.example.users;


import com.example.coreapi.users.queries.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EntityScan(basePackageClasses = { UserInfo.class, SagaEntry.class, TokenEntry.class })
@SpringBootApplication
@EnableDiscoveryClient
public class UsersMicroServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(UsersMicroServiceApplication.class, args);
    }

    @Autowired
    public void configureSerializers(ObjectMapper objectMapper) {
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    }
}
