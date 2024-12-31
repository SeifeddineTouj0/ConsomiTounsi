package tn.fst.igl5.delivery_microservice.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.fst.igl5.delivery_microservice.domain.Delivery;
import tn.fst.igl5.delivery_microservice.domain.DeliveryPerson;
import tn.fst.igl5.delivery_microservice.model.DeliveryPersonDTO;
import tn.fst.igl5.delivery_microservice.repos.DeliveryPersonRepository;
import tn.fst.igl5.delivery_microservice.repos.DeliveryRepository;
import tn.fst.igl5.delivery_microservice.util.NotFoundException;
import tn.fst.igl5.delivery_microservice.util.ReferencedWarning;

@Service
public class DeliveryPersonService {
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryRepository deliveryRepository;

    public DeliveryPersonService(final DeliveryPersonRepository deliveryPersonRepository, final DeliveryRepository deliveryRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public List<DeliveryPersonDTO> findAll() {
        final List<DeliveryPerson> deliveryPersons = deliveryPersonRepository.findAll(Sort.by("id"));
        return deliveryPersons.stream().map(deliveryPerson -> mapToDTO(deliveryPerson, new DeliveryPersonDTO())).toList();
    }

    public DeliveryPersonDTO get(final String id) {
        return deliveryPersonRepository.findById(id).map(deliveryPerson -> mapToDTO(deliveryPerson, new DeliveryPersonDTO())).orElseThrow(NotFoundException::new);
    }

    public String create(final DeliveryPersonDTO deliveryPersonDTO, String id) {
        final DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setId(id);
        mapToEntity(deliveryPersonDTO, deliveryPerson);
        return deliveryPersonRepository.save(deliveryPerson).getId();
    }

    public void update(final String id, final DeliveryPersonDTO deliveryPersonDTO) {
        final DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(deliveryPersonDTO, deliveryPerson);
        deliveryPersonRepository.save(deliveryPerson);
    }

    public void delete(final String id) {
        deliveryPersonRepository.deleteById(id);
    }

    private DeliveryPersonDTO mapToDTO(final DeliveryPerson deliveryPerson, final DeliveryPersonDTO deliveryPersonDTO) {
        deliveryPersonDTO.setName(deliveryPerson.getName());
        deliveryPersonDTO.setPhone(deliveryPerson.getPhone());
        deliveryPersonDTO.setEmail(deliveryPerson.getEmail());
        deliveryPersonDTO.setAddress(deliveryPerson.getAddress());
        deliveryPersonDTO.setVehiculeType(deliveryPerson.getVehiculeType());
        deliveryPersonDTO.setPlateNumber(deliveryPerson.getPlateNumber());
        return deliveryPersonDTO;
    }

    private DeliveryPerson mapToEntity(final DeliveryPersonDTO deliveryPersonDTO, final DeliveryPerson deliveryPerson) {
        deliveryPerson.setName(deliveryPersonDTO.getName());
        deliveryPerson.setPhone(deliveryPersonDTO.getPhone());
        deliveryPerson.setEmail(deliveryPersonDTO.getEmail());
        deliveryPerson.setAddress(deliveryPersonDTO.getAddress());
        deliveryPerson.setVehiculeType(deliveryPersonDTO.getVehiculeType());
        deliveryPerson.setPlateNumber(deliveryPersonDTO.getPlateNumber());
        return deliveryPerson;
    }

    public ReferencedWarning getReferencedWarning(final String id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id).orElseThrow(NotFoundException::new);
        final Delivery deliveryPersonDelivery = deliveryRepository.findFirstByDeliveryPerson(deliveryPerson);
        if (deliveryPersonDelivery != null) {
            referencedWarning.setKey("deliveryPerson.delivery.deliveryPerson.referenced");
            referencedWarning.addParam(deliveryPersonDelivery.getId());
            return referencedWarning;
        }
        return null;
    }
}
