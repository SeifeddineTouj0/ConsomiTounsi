package com.example.coreapi.delivery.command;

import com.example.coreapi.delivery.OrderDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AffectDeliveryPersonCommand {
    @TargetAggregateIdentifier
    String id;

    OrderDetailsDTO orderDetailsDTO;

}
