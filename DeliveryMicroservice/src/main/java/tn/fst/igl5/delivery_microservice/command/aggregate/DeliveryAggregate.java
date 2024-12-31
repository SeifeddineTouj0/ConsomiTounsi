package tn.fst.igl5.delivery_microservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateIdentifier;
import tn.fst.igl5.delivery_microservice.command.command.CreateDeliveryCommand;
import tn.fst.igl5.delivery_microservice.command.command.DeleteDeliveryCommand;
import tn.fst.igl5.delivery_microservice.command.command.UpdateDeliveryCommand;
import tn.fst.igl5.delivery_microservice.command.event.DeliveryCreatedEvent;
import tn.fst.igl5.delivery_microservice.command.event.DeliveryDeletedEvent;
import tn.fst.igl5.delivery_microservice.command.event.DeliveryUpdatedEvent;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class DeliveryAggregate {

    @AggregateIdentifier
    private String id;
    private DeliveryDTO deliveryDTO;

    public DeliveryAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public DeliveryAggregate(CreateDeliveryCommand command) {
        apply(new DeliveryCreatedEvent(command.getId(), command.getDeliveryDTO()));
    }

    @CommandHandler
    public void handle(UpdateDeliveryCommand command) {
        apply(new DeliveryUpdatedEvent(command.getId(), command.getDeliveryDTO()));
    }

    @CommandHandler
    public void handle(DeleteDeliveryCommand command) {
        apply(new DeliveryDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    protected void on(DeliveryCreatedEvent event) {
        this.id = event.getId();
        this.deliveryDTO = event.getDeliveryDTO();
    }

    @EventSourcingHandler
    protected void on(DeliveryUpdatedEvent event) {
        this.deliveryDTO = event.getDeliveryDTO();
    }

    @EventSourcingHandler
    protected void on(DeliveryDeletedEvent event) {
    }
}