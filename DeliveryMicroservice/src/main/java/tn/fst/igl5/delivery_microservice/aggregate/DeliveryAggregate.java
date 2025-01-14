package tn.fst.igl5.delivery_microservice.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateIdentifier;
import com.example.coreapi.delivery.command.CreateDeliveryCommand;
import com.example.coreapi.delivery.command.DeleteDeliveryCommand;
import com.example.coreapi.delivery.command.UpdateDeliveryCommand;
import com.example.coreapi.delivery.event.DeliveryCreatedEvent;
import com.example.coreapi.delivery.event.DeliveryDeletedEvent;
import com.example.coreapi.delivery.event.DeliveryUpdatedEvent;
import com.example.coreapi.delivery.DeliveryDTO;

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