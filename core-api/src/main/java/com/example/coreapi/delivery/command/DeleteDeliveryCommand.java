package com.example.coreapi.delivery.command;

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
