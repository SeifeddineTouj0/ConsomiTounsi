package com.jellali.forum.Command.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SujetCleanupScheduler {

    private final DeleteInactiveSujetsCommandHandler deleteHandler;

    @Autowired
    public SujetCleanupScheduler(DeleteInactiveSujetsCommandHandler deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    // Runs daily at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupInactiveSujets() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        DeleteInactiveSujetsCommand command = new DeleteInactiveSujetsCommand(thirtyDaysAgo);
        deleteHandler.handle(command);
    }
}
