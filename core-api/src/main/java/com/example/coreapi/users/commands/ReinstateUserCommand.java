package com.example.coreapi.users.commands;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReinstateUserCommand {
    String id;
}
