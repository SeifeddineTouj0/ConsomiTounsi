package com.jellali.forum.Command.Delete;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeleteSujetCommand {

    @TargetAggregateIdentifier
    private Long id;

    public Long getId() {
        return id;
    }

    public DeleteSujetCommand(Long id) {
        this.id = id;
    }
}

