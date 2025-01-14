package tn.fst.igl5.delivery_microservice.rest;

import com.example.coreapi.delivery.GetDeliveryFeesQuery;
import com.example.coreapi.delivery.OrderDetailsDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.fst.igl5.delivery_microservice.command.CreateDeliveryCommand;
import tn.fst.igl5.delivery_microservice.command.DeleteDeliveryCommand;
import tn.fst.igl5.delivery_microservice.command.UpdateDeliveryCommand;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllDeliveriesQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetDeliveryQuery;


@RestController
@RequestMapping(value = "/api/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryResource {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public DeliveryResource(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    // Query: Get all deliveries
    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = queryGateway.query(
                new GetAllDeliveriesQuery(),
                ResponseTypes.multipleInstancesOf(DeliveryDTO.class)
        ).join();
        return ResponseEntity.ok(deliveries);
    }

    // Query: Get delivery by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDelivery(@PathVariable String id) {
        DeliveryDTO delivery = queryGateway.query(
                new GetDeliveryQuery(id),
                DeliveryDTO.class
        ).join();
        return ResponseEntity.ok(delivery);
    }

    // Command: Create delivery
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createAndAssignDelivery(@RequestBody @Valid DeliveryDTO deliveryDTO) {
        String id = UUID.randomUUID().toString();
        commandGateway.sendAndWait(new CreateDeliveryCommand(id, deliveryDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Command: Update delivery
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDelivery(@PathVariable String id, @RequestBody @Valid DeliveryDTO deliveryDTO) {
        commandGateway.sendAndWait(new UpdateDeliveryCommand(id, deliveryDTO));
        return ResponseEntity.ok().build();
    }

    // Command: Delete delivery
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDelivery(@PathVariable String id) {
        commandGateway.sendAndWait(new DeleteDeliveryCommand(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/caluclateFees")
    public CompletableFuture<Double> deliveryFees(@RequestBody @Valid OrderDetailsDTO orderDetailsDTO){
        //return orderDetailsDTO;
        return queryGateway.query(new GetDeliveryFeesQuery(orderDetailsDTO), Double.class);
    }

}
