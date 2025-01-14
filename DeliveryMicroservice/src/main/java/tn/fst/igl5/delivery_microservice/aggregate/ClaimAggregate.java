package tn.fst.igl5.delivery_microservice.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.example.coreapi.delivery.CreateClaimCommand;
import com.example.coreapi.delivery.command.DeleteClaimCommand;
import com.example.coreapi.delivery.command.UpdateClaimCommand;
import com.example.coreapi.delivery.ClaimCreatedEvent;
import com.example.coreapi.delivery.event.ClaimDeletedEvent;
import com.example.coreapi.delivery.event.ClaimUpdatedEvent;
import com.example.coreapi.delivery.ClaimDTO;
@Aggregate
public class ClaimAggregate {
    @AggregateIdentifier
    private String claimId;
    private ClaimDTO claimDTO;

    public ClaimAggregate() {        // Required by Axon
    }

    @CommandHandler
    public ClaimAggregate(CreateClaimCommand command) {
        Assert.notNull(command.getClaimDTO(), () -> "ClaimDTO cannot be null");
        AggregateLifecycle.apply(new ClaimCreatedEvent(command.getClaimId(), command.getClaimDTO()));
    }

    @EventSourcingHandler
    public void on(ClaimCreatedEvent event) {
        this.claimId = event.getClaimId();
        this.claimDTO = event.getClaimDTO();
    }

    @CommandHandler
    public void handle(UpdateClaimCommand command) {
        AggregateLifecycle.apply(new ClaimUpdatedEvent(command.getClaimId(), command.getClaimDTO()));
    }

    @EventSourcingHandler
    public void on(ClaimUpdatedEvent event) {
        this.claimDTO = event.getClaimDTO();
    }

    @CommandHandler
    public void handle(DeleteClaimCommand command) {
        AggregateLifecycle.apply(new ClaimDeletedEvent(command.getClaimId()));
    }

    @EventSourcingHandler
    public void on(ClaimDeletedEvent event) {
    }
}
