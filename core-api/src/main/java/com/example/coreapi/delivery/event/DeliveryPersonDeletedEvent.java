package com.example.coreapi.delivery.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryPersonDeletedEvent {
    String id;
}
