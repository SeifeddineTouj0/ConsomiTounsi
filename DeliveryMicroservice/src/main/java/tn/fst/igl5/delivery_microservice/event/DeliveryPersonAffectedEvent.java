package tn.fst.igl5.delivery_microservice.event;

import com.example.coreapi.delivery.OrderDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonAffectedEvent {
    String id;
    DeliveryDTO deliveryDTO;
}
