package com.example.coreapi.users.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;
}
