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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClaimProjection {
    private final Map<String, ClaimDTO> claims = new ConcurrentHashMap<>();

    @EventHandler
    public void on(ClaimCreatedEvent event) {
        claims.put(event.getClaimId(), event.getClaimDTO());
    }

    @EventHandler
    public void on(ClaimUpdatedEvent event) {
        claims.put(event.getClaimId(), event.getClaimDTO());
    }

    @EventHandler
    public void on(ClaimDeletedEvent event) {
        claims.remove(event.getClaimId());
    }

    @QueryHandler
    public List<ClaimDTO> handle(GetAllClaimsQuery query) {
        return new ArrayList<>(claims.values());
    }

    @QueryHandler
    public ClaimDTO handle(GetClaimQuery query) {
        return claims.get(query.getClaimId());
    }
}
