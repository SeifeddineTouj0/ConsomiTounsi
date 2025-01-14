package com.example.coreapi.delivery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryPersonDTO {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Vehicule vehiculeType;
    private String plateNumber;
    private Double currentLat;
    private Double currentLng;
    private boolean available;
}
