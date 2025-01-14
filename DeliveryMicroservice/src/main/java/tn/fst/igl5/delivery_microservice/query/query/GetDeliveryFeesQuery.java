package tn.fst.igl5.delivery_microservice.query.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.fst.igl5.delivery_microservice.model.OrderDetailsDTO;

@Getter
@Setter
@AllArgsConstructor
public class GetDeliveryFeesQuery {
    OrderDetailsDTO order;
}
