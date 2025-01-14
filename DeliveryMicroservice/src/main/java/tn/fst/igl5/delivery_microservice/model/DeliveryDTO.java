package tn.fst.igl5.delivery_microservice.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDTO {
    private String id;
    private Double locationLat;
    private Double locationLon;
    @Size(max = 255)
    private String orderId;
    private String deliveryPerson;
}
