package tn.fst.igl5.delivery_microservice.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fst.igl5.delivery_microservice.domain.Delivery;
import tn.fst.igl5.delivery_microservice.domain.DeliveryPerson;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;
import tn.fst.igl5.delivery_microservice.repos.DeliveryPersonRepository;
import tn.fst.igl5.delivery_microservice.repos.DeliveryRepository;
import tn.fst.igl5.delivery_microservice.util.NotFoundException;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryService(final DeliveryRepository deliveryRepository, final DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    public List<DeliveryDTO> findAll() {
        final List<Delivery> deliveries = deliveryRepository.findAll(Sort.by("id"));
        return deliveries.stream().map(delivery -> mapToDTO(delivery, new DeliveryDTO())).toList();
    }

    public DeliveryDTO get(final Long id) {
        return deliveryRepository.findById(id).map(delivery -> mapToDTO(delivery, new DeliveryDTO())).orElseThrow(NotFoundException::new);
    }

    public Long create(final DeliveryDTO deliveryDTO) {
        final Delivery delivery = new Delivery();
        mapToEntity(deliveryDTO, delivery);
        return deliveryRepository.save(delivery).getId();
    }

    public void update(final Long id, final DeliveryDTO deliveryDTO) {
        final Delivery delivery = deliveryRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(deliveryDTO, delivery);
        deliveryRepository.save(delivery);
    }

    public void delete(final Long id) {
        deliveryRepository.deleteById(id);
    }

    private DeliveryDTO mapToDTO(final Delivery delivery, final DeliveryDTO deliveryDTO) {
        deliveryDTO.setId(delivery.getId());
        deliveryDTO.setLocationLat(delivery.getLocationLat());
        deliveryDTO.setLocationLon(delivery.getLocationLon());
        deliveryDTO.setOrderId(delivery.getOrderId());
        deliveryDTO.setDeliveryPerson(delivery.getDeliveryPerson() == null ? null : delivery.getDeliveryPerson().getId());
        return deliveryDTO;
    }

    private Delivery mapToEntity(final DeliveryDTO deliveryDTO, final Delivery delivery) {
        delivery.setLocationLat(deliveryDTO.getLocationLat());
        delivery.setLocationLon(deliveryDTO.getLocationLon());
        delivery.setOrderId(deliveryDTO.getOrderId());
        final DeliveryPerson deliveryPerson = deliveryDTO.getDeliveryPerson() == null ? null : deliveryPersonRepository.findById(deliveryDTO.getDeliveryPerson()).orElseThrow(() -> new NotFoundException("deliveryPerson not found"));
        delivery.setDeliveryPerson(deliveryPerson);
        return delivery;
    }
}
