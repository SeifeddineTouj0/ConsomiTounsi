package com.jellali.forum.Event;

public class SujetDeletedEvent {
    private final Long id;

    public SujetDeletedEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
