package tn.fst.igl5.delivery_microservice.query.projection;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import tn.fst.igl5.delivery_microservice.event.DeliveryPersonCreatedEvent;
import tn.fst.igl5.delivery_microservice.event.DeliveryPersonDeletedEvent;
import tn.fst.igl5.delivery_microservice.event.DeliveryPersonUpdatedEvent;
import tn.fst.igl5.delivery_microservice.model.DeliveryPersonDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllDeliveryPeopleQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetDeliveryPersonQuery;
import tn.fst.igl5.delivery_microservice.service.DeliveryPersonService;

import java.util.List;

@Component
public class DeliveryPersonProjection {
    private final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonProjection(DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }

    @EventHandler
    public void on(DeliveryPersonCreatedEvent event) {
        deliveryPersonService.create(event.getDeliveryPersonDTO(), event.getId());
    }

    @EventHandler
    public void on(DeliveryPersonUpdatedEvent event) {
        deliveryPersonService.update(event.getId(), event.getDeliveryPersonDTO());
    }

    @EventHandler
    public void on(DeliveryPersonDeletedEvent event) {
        deliveryPersonService.delete(event.getId());
    }



    @QueryHandler
    public List<DeliveryPersonDTO> handle(GetAllDeliveryPeopleQuery query) {
        return deliveryPersonService.findAll();
    }

    @QueryHandler
    public DeliveryPersonDTO handle(GetDeliveryPersonQuery query) {
        return deliveryPersonService.get(query.getId());
    }
}
