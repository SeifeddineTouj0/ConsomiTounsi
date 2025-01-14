package tn.fst.igl5.delivery_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.DeliveryPersonDTO;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryPersonCreatedEvent {
    String id;
    DeliveryPersonDTO deliveryPersonDTO;
}
