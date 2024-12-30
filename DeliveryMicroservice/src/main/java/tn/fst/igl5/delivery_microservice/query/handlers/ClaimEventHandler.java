package tn.fst.igl5.delivery_microservice.query.handlers;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import tn.fst.igl5.delivery_microservice.command.event.ClaimCreatedEvent;
import tn.fst.igl5.delivery_microservice.command.event.ClaimDeletedEvent;
import tn.fst.igl5.delivery_microservice.command.event.ClaimUpdatedEvent;
import tn.fst.igl5.delivery_microservice.domain.Claim;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;
import tn.fst.igl5.delivery_microservice.repos.ClaimRepository;

@Component
public class ClaimEventHandler {
    private final ClaimRepository claimRepository;

    public ClaimEventHandler(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @EventHandler
    public void on(ClaimCreatedEvent event) {
        Claim claim = new Claim();
        claim.setId(event.getClaimId());
        claim.setContent(event.getClaimDTO().getContent());
        claim.setStatus(event.getClaimDTO().getStatus());
        // Set other fields as needed
        claimRepository.save(claim);
    }

    @EventHandler
    public void on(ClaimUpdatedEvent event) {
        Claim existingClaim = claimRepository.findById(event.getClaimId())
                .orElseThrow(() -> new IllegalArgumentException("Claim not found"));
        existingClaim.setContent(event.getClaimDTO().getContent());
        existingClaim.setStatus(event.getClaimDTO().getStatus());
        // Update other fields as needed
        claimRepository.save(existingClaim);
    }

    @EventHandler
    public void on(ClaimDeletedEvent event) {
        claimRepository.deleteById(event.getClaimId());
    }
}
