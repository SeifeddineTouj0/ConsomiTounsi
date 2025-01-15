package com.example.coreapi.users.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDeletedEvent {
    String id;
}
