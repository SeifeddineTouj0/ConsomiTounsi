package tn.fst.igl5.delivery_microservice.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fst.igl5.delivery_microservice.domain.Delivery;
import tn.fst.igl5.delivery_microservice.domain.DeliveryPerson;
import tn.fst.igl5.delivery_microservice.helper.DistanceHelper;
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

    public DeliveryDTO get(final String id) {
        return deliveryRepository.findById(id).map(delivery -> mapToDTO(delivery, new DeliveryDTO())).orElseThrow(NotFoundException::new);
    }

    public String create(final DeliveryDTO deliveryDTO, String id) {
        final Delivery delivery = new Delivery();
        delivery.setId(id);
        mapToEntity(deliveryDTO, delivery);
        return deliveryRepository.save(delivery).getId();
    }

    public void update(final String id, final DeliveryDTO deliveryDTO) {
        final Delivery delivery = deliveryRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(deliveryDTO, delivery);
        deliveryRepository.save(delivery);
    }

    public void delete(final String id) {
        deliveryRepository.deleteById(id);
    }

    public Double calculateFees(double targetLat, double targetLng, double sourceLat, double sourceLng) {
        return calculateFees(targetLat, targetLng, sourceLat, sourceLng, null);
    }

    public Double calculateFees(double targetLat, double targetLng, double sourceLat, double sourceLng, List<Double> Weights) {
        double prix=0;
        double distance= DistanceHelper.calculateDistance(targetLat,targetLng,sourceLat,sourceLng);
        prix = (distance/1000)*0.5;
        for(Double weight:Weights){
            prix += weight*0.3;
        }
        return (double) Math.round(prix);
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
