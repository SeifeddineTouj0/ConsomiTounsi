package tn.fst.igl5.delivery_microservice.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeliveryDTO {

    private Long id;

    private Long locationLat;

    private Long locationLon;

    @Size(max = 255)
    private String orderId;

    private Long deliveryPerson;

}
