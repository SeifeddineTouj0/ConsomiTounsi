package com.example.coreapi.delivery.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.example.coreapi.delivery.DeliveryDTO;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryUpdatedEvent {
    String id;
    DeliveryDTO deliveryDTO;
}
