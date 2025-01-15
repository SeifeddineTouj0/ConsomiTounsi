package com.jellali.forum.Controller;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://user-service-url").build();
    }

    public boolean isValidUser(String userId) {
        Mono<Boolean> result = webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(Boolean.class);

        return result.block();  // Blocking here for simplicity (for now)
    }
}

