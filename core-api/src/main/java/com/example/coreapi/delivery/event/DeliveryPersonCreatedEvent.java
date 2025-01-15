package com.example.coreapi.delivery.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.example.coreapi.delivery.DeliveryPersonDTO;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryPersonCreatedEvent {
    String id;
    DeliveryPersonDTO deliveryPersonDTO;
}
