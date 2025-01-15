package tn.fst.igl5.delivery_microservice.projection;


import com.example.coreapi.delivery.GetDeliveryFeesQuery;
import com.example.coreapi.delivery.command.CreateDeliveryCommand;
import com.example.coreapi.ventes.payment.PaymentCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import com.example.coreapi.delivery.event.DeliveryCreatedEvent;
import com.example.coreapi.delivery.event.DeliveryDeletedEvent;
import com.example.coreapi.delivery.event.DeliveryUpdatedEvent;
import com.example.coreapi.delivery.DeliveryDTO;
import com.example.coreapi.delivery.query.GetAllDeliveriesQuery;
import com.example.coreapi.delivery.query.GetDeliveryQuery;
import tn.fst.igl5.delivery_microservice.service.DeliveryService;

import java.util.List;
import java.util.UUID;

@Component
public class DeliveryProjection {
    private final DeliveryService deliveryService;
    private final CommandGateway commandGateway;

    public DeliveryProjection(DeliveryService deliveryService, CommandGateway commandGateway) {
        this.deliveryService = deliveryService;
        this.commandGateway = commandGateway;
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

    @EventHandler
    public void on(PaymentCreatedEvent event){
        String id = UUID.randomUUID().toString();
        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setOrderId(event.getPaymentId());
        deliveryDTO.setLocationLat(event.getUserAdressLat());
        deliveryDTO.setLocationLon(event.getUserAdressLong());
        commandGateway.sendAndWait(new CreateDeliveryCommand(id, deliveryDTO));
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
        return deliveryService.calculateFees(query.getOrder().getTargetLat(),query.getOrder().getTargetLng(),36.83397342793626, 10.147848486264067,query.getOrder().getProducts());
    }
}
