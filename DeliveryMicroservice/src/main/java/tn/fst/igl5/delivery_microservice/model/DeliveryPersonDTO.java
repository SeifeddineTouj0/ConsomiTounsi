package tn.fst.igl5.delivery_microservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryPersonDTO {
    private String id;
    @NotNull
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String phone;
    @Size(max = 255)
    private String email;
    @Size(max = 255)
    private String address;
    private Vehicule vehiculeType;
    @Size(max = 255)
    private String plateNumber;
    private Double currentLat;
    private Double currentLng;
    private boolean available;
}
