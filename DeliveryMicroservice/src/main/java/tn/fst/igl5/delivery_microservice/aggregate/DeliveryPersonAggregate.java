package tn.fst.igl5.delivery_microservice.aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateIdentifier;
import com.example.coreapi.delivery.command.CreateDeliveryPersonCommand;
import com.example.coreapi.delivery.command.DeleteDeliveryPersonCommand;
import com.example.coreapi.delivery.command.UpdateDeliveryPersonCommand;
import com.example.coreapi.delivery.event.DeliveryPersonCreatedEvent;
import com.example.coreapi.delivery.event.DeliveryPersonDeletedEvent;
import com.example.coreapi.delivery.event.DeliveryPersonUpdatedEvent;
import com.example.coreapi.delivery.DeliveryPersonDTO;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class DeliveryPersonAggregate {

    @AggregateIdentifier
    private String id;
    private DeliveryPersonDTO deliveryPersonDTO;

    public DeliveryPersonAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public DeliveryPersonAggregate(CreateDeliveryPersonCommand command) {
        // Perform any validation here if necessary
        apply(new DeliveryPersonCreatedEvent(command.getId(), command.getDeliveryPersonDTO()));
    }

    @CommandHandler
    public void handle(UpdateDeliveryPersonCommand command) {
        // Perform any validation here if necessary
        apply(new DeliveryPersonUpdatedEvent(command.getId(), command.getDeliveryPersonDTO()));
    }

    @CommandHandler
    public void handle(DeleteDeliveryPersonCommand command) {
        apply(new DeliveryPersonDeletedEvent(command.getId()));
    }


    @EventSourcingHandler
    protected void on(DeliveryPersonCreatedEvent event) {
        this.id = event.getId();
        this.deliveryPersonDTO = event.getDeliveryPersonDTO();
    }

    @EventSourcingHandler
    protected void on(DeliveryPersonUpdatedEvent event) {
        this.deliveryPersonDTO = event.getDeliveryPersonDTO();
    }

    @EventSourcingHandler
    protected void on(DeliveryPersonDeletedEvent event) {
    }


}
