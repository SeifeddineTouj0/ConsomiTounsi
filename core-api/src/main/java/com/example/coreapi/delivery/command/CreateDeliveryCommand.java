package com.example.coreapi.delivery.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.example.coreapi.delivery.DeliveryDTO;

@Getter
@Setter
@AllArgsConstructor
public class CreateDeliveryCommand {
    @TargetAggregateIdentifier
    String id;
    DeliveryDTO deliveryDTO;
}
