package tn.fst.igl5.delivery_microservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class DeleteDeliveryCommand {
    @TargetAggregateIdentifier
    String id;
}
