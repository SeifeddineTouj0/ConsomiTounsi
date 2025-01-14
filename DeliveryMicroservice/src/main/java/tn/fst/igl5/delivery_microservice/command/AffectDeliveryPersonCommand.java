package tn.fst.igl5.delivery_microservice.command;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import tn.fst.igl5.delivery_microservice.model.OrderDetailsDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AffectDeliveryPersonCommand {
    @TargetAggregateIdentifier
    String id;

    OrderDetailsDTO orderDetailsDTO;

}
