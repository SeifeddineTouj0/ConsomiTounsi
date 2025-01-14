package com.example.coreapi.users.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchUserByIdQuery {
    String id;
}
