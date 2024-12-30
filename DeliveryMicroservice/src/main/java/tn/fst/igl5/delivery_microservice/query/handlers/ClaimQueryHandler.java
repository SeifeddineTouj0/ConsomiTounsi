package tn.fst.igl5.delivery_microservice.query.handlers;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import tn.fst.igl5.delivery_microservice.domain.Claim;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllClaimsQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetClaimQuery;
import tn.fst.igl5.delivery_microservice.repos.ClaimRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClaimQueryHandler {
    private final List<ClaimDTO> claims = new ArrayList<>();
    private final ClaimRepository claimRepository;

    public ClaimQueryHandler(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @QueryHandler
    public List<Claim> handle(GetAllClaimsQuery query) {
        return claimRepository.findAll();
    }

    @QueryHandler
    public Optional<Claim> handle(GetClaimQuery query) {
        return claimRepository.findById(query.getClaimId());
    }
}
