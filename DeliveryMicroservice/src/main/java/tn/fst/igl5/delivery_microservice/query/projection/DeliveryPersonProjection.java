package tn.fst.igl5.delivery_microservice.query.projection;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import com.example.coreapi.delivery.event.DeliveryPersonCreatedEvent;
import com.example.coreapi.delivery.event.DeliveryPersonDeletedEvent;
import com.example.coreapi.delivery.event.DeliveryPersonUpdatedEvent;
import com.example.coreapi.delivery.DeliveryPersonDTO;
import com.example.coreapi.delivery.query.GetAllDeliveryPeopleQuery;
import com.example.coreapi.delivery.query.GetDeliveryPersonQuery;
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
