package tn.fst.igl5.delivery_microservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fst.igl5.delivery_microservice.domain.DeliveryPerson;

import java.util.List;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, String> {
    List<DeliveryPerson> findDeliveryPersonByAvailable(boolean available);
}
