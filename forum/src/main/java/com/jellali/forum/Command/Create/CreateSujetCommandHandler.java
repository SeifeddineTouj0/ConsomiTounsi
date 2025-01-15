package com.jellali.forum.Command.Create;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateSujetCommandHandler {

    @Autowired
    private CommandGateway commandGateway;

    public void handle(CreateSujetCommand command) {
        commandGateway.sendAndWait(command);
    }
}
