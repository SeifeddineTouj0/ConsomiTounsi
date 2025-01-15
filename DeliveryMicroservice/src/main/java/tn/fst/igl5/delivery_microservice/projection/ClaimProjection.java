package tn.fst.igl5.delivery_microservice.projection;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import com.example.coreapi.delivery.ClaimCreatedEvent;
import com.example.coreapi.delivery.event.ClaimDeletedEvent;
import com.example.coreapi.delivery.event.ClaimUpdatedEvent;
import com.example.coreapi.delivery.ClaimDTO;
import com.example.coreapi.delivery.GetAllClaimsQuery;
import com.example.coreapi.delivery.GetClaimQuery;
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
