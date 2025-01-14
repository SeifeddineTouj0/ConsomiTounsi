package com.consommitounsi.cagnottes.cagnottes.events;

import lombok.Getter;

@Getter
public class CagnotteSupprimeeEvent {
    private final String cagnotteId;

    public CagnotteSupprimeeEvent(String cagnotteId) {
        this.cagnotteId = cagnotteId;
    }
}
