package tn.fst.igl5.delivery_microservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryCreatedEvent {
    String id;
    DeliveryDTO deliveryDTO;
}
