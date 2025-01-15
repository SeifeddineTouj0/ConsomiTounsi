package com.jellali.forum.Event;

import java.time.LocalDateTime;

public class InactiveSujetsDeletedEvent {
    private final LocalDateTime cutoffDate;

    public InactiveSujetsDeletedEvent(LocalDateTime cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

    public LocalDateTime getCutoffDate() {
        return cutoffDate;
    }
}
