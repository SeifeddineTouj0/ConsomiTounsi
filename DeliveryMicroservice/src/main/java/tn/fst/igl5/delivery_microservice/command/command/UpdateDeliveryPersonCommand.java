package tn.fst.igl5.delivery_microservice.command.command;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import tn.fst.igl5.delivery_microservice.model.DeliveryPersonDTO;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDeliveryPersonCommand {
    @TargetAggregateIdentifier
    String id;
    DeliveryPersonDTO deliveryPersonDTO;
}
