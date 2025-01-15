package com.example.coreapi.delivery.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.coreapi.delivery.DeliveryDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonAffectedEvent {
    String id;
    DeliveryDTO deliveryDTO;
}
