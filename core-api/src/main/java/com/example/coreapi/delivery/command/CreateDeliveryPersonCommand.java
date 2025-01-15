package com.example.coreapi.delivery.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.example.coreapi.delivery.DeliveryPersonDTO;

@Getter
@Setter
@AllArgsConstructor
public class CreateDeliveryPersonCommand {
    @TargetAggregateIdentifier
    String id;
    DeliveryPersonDTO deliveryPersonDTO;
}
