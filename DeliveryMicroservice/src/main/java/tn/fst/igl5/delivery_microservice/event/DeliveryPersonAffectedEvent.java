package tn.fst.igl5.delivery_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.OrderDetailsDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonAffectedEvent {
    String id;
    OrderDetailsDTO orderDetailsDTO;
}
