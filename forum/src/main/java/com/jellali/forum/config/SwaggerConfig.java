package com.jellali.forum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI defineOpenApi() {
        // Define the server
        Server server = new Server();
        server.setUrl("http://localhost:4000"); // Update to match your application port
        server.setDescription("Development Server");

        // Define the contact information
        Contact myContact = new Contact();
        myContact.setName("malek");
        myContact.setEmail("your.email@gmail.com");

        // Define the API info
        Info information = new Info()
                .title("Forum Management System API")
                .version("1.0")
                .description("This API exposes endpoints to manage forum topics and users.")
                .contact(myContact);

        // Return the OpenAPI instance
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
