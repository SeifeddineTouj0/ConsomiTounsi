package tn.fst.igl5.delivery_microservice.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fst.igl5.delivery_microservice.domain.Claim;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;
import tn.fst.igl5.delivery_microservice.repos.ClaimRepository;
import tn.fst.igl5.delivery_microservice.util.NotFoundException;

@Service
public class ClaimService {
    private final ClaimRepository claimRepository;

    public ClaimService(final ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public List<ClaimDTO> findAll() {
        final List<Claim> claims = claimRepository.findAll(Sort.by("id"));
        return claims.stream().map(claim -> mapToDTO(claim, new ClaimDTO())).toList();
    }

    public ClaimDTO get(final String id) {
        return claimRepository.findById(id).map(claim -> mapToDTO(claim, new ClaimDTO())).orElseThrow(NotFoundException::new);
    }

    public String create(final ClaimDTO claimDTO,String claimId) {
        final Claim claim = new Claim();
        claim.setId(claimId);
        mapToEntity(claimDTO, claim);
        return claimRepository.save(claim).getId();
    }

    public void update(final String id, final ClaimDTO claimDTO) {
        final Claim claim = claimRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(claimDTO, claim);
        claimRepository.save(claim);
    }

    public void delete(final String id) {
        claimRepository.deleteById(id);
    }

    private ClaimDTO mapToDTO(final Claim claim, final ClaimDTO claimDTO) {
        claimDTO.setContent(claim.getContent());
        claimDTO.setDecision(claim.getDecision());
        claimDTO.setStatus(claim.getStatus());
        return claimDTO;
    }

    private Claim mapToEntity(final ClaimDTO claimDTO, final Claim claim) {
        claim.setContent(claimDTO.getContent());
        claim.setDecision(claimDTO.getDecision());
        claim.setStatus(claimDTO.getStatus());
        return claim;
    }
}
