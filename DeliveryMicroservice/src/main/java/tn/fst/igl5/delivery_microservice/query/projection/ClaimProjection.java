package tn.fst.igl5.delivery_microservice.query.projection;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import tn.fst.igl5.delivery_microservice.command.event.ClaimCreatedEvent;
import tn.fst.igl5.delivery_microservice.command.event.ClaimDeletedEvent;
import tn.fst.igl5.delivery_microservice.command.event.ClaimUpdatedEvent;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllClaimsQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetClaimQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetDeliveryFeesQuery;
import tn.fst.igl5.delivery_microservice.service.ClaimService;

import java.util.List;

@Component
public class ClaimProjection {
    ClaimService claimService;
    public ClaimProjection(ClaimService claimService) {
        this.claimService = claimService;
    }

    @EventHandler
    public void on(ClaimCreatedEvent event) {
        claimService.create(event.getClaimDTO(), event.getClaimId());
    }

    @EventHandler
    public void on(ClaimUpdatedEvent event) {
        claimService.update(event.getClaimId(), event.getClaimDTO());
    }

    @EventHandler
    public void on(ClaimDeletedEvent event) {
        claimService.delete(event.getClaimId());
    }

    @QueryHandler
    public List<ClaimDTO> handle(GetAllClaimsQuery query) {
        return claimService.findAll();
    }

    @QueryHandler
    public ClaimDTO handle(GetClaimQuery query) {
        return claimService.get(query.getClaimId());
    }


}
