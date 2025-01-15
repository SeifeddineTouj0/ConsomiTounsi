package com.jellali.forum.Command.Delete;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

public class DeleteInactiveSujetsCommand {

    @TargetAggregateIdentifier
    private final LocalDateTime cutoffDate;

    public DeleteInactiveSujetsCommand(LocalDateTime cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

    public LocalDateTime getCutoffDate() {
        return cutoffDate;
    }
}
