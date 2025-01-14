package com.example.coreapi.delivery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDTO {
    private String id;
    private Double locationLat;
    private Double locationLon;
    private String orderId;
    private String deliveryPerson;
}
