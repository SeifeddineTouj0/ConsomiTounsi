package com.example.coreapi.users.queries;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchUserByNameOrEmailQuery {
    String username;
    String email;
}
