package com.example.coreapi.users.queries;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserInfo implements Serializable {
    private String id;
    private String username;
    private String email;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;

}
