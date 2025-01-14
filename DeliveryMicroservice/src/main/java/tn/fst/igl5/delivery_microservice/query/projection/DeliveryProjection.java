package tn.fst.igl5.delivery_microservice.query.projection;

import com.example.coreapi.delivery.GetDeliveryFeesQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import tn.fst.igl5.delivery_microservice.event.DeliveryCreatedEvent;
import tn.fst.igl5.delivery_microservice.event.DeliveryDeletedEvent;
import tn.fst.igl5.delivery_microservice.event.DeliveryUpdatedEvent;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllDeliveriesQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetDeliveryQuery;
import tn.fst.igl5.delivery_microservice.service.DeliveryService;

import java.util.List;

@Component
public class DeliveryProjection {
    private final DeliveryService deliveryService;

    public DeliveryProjection(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @EventHandler
    public void on(DeliveryCreatedEvent event) {
        deliveryService.create(event.getDeliveryDTO(), event.getId());
    }

    @EventHandler
    public void on(DeliveryUpdatedEvent event) {
        deliveryService.update(event.getId(), event.getDeliveryDTO());
    }

    @EventHandler
    public void on(DeliveryDeletedEvent event) {
        deliveryService.delete(event.getId());
    }

    @QueryHandler
    public List<DeliveryDTO> handle(GetAllDeliveriesQuery query) {
        return deliveryService.findAll();
    }

    @QueryHandler
    public DeliveryDTO handle(GetDeliveryQuery query) {
        return deliveryService.get(query.getId());
    }

    @QueryHandler
    public Double handle(GetDeliveryFeesQuery query){
        return deliveryService.calculateFees(query.getOrder().getTargetLat(),query.getOrder().getTargetLng(),36.83397342793626, 10.147848486264067,query.getOrder().getWeights());
    }
}
