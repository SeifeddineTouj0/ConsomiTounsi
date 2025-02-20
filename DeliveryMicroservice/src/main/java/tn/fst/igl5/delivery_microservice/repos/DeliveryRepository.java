package tn.fst.igl5.delivery_microservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fst.igl5.delivery_microservice.domain.Delivery;
import tn.fst.igl5.delivery_microservice.domain.DeliveryPerson;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    Delivery findFirstByDeliveryPerson(DeliveryPerson deliveryPerson);
}
