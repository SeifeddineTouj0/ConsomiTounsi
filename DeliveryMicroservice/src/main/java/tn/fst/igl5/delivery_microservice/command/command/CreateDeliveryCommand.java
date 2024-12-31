package tn.fst.igl5.delivery_microservice.command.command;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;

@Getter
@Setter
@AllArgsConstructor
public class CreateDeliveryCommand {
    @TargetAggregateIdentifier
    String id;
    DeliveryDTO deliveryDTO;
}
