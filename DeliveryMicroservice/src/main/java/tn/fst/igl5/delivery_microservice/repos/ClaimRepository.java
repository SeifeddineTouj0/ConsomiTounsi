package tn.fst.igl5.delivery_microservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fst.igl5.delivery_microservice.domain.Claim;

public interface ClaimRepository extends JpaRepository<Claim, String> {
}
