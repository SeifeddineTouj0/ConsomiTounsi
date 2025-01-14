package tn.fst.igl5.delivery_microservice.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.ast.Or;
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
import tn.fst.igl5.delivery_microservice.command.command.AffectDeliveryPersonCommand;
import tn.fst.igl5.delivery_microservice.command.command.CreateDeliveryPersonCommand;
import tn.fst.igl5.delivery_microservice.command.command.DeleteDeliveryPersonCommand;
import tn.fst.igl5.delivery_microservice.command.command.UpdateDeliveryPersonCommand;
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;
import tn.fst.igl5.delivery_microservice.model.DeliveryPersonDTO;
import tn.fst.igl5.delivery_microservice.model.OrderDetailsDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllDeliveryPeopleQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetDeliveryPersonQuery;
import tn.fst.igl5.delivery_microservice.service.DeliveryPersonService;
import tn.fst.igl5.delivery_microservice.util.ReferencedException;
import tn.fst.igl5.delivery_microservice.util.ReferencedWarning;

@RestController
@RequestMapping(value = "/api/deliveryPeople", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryPersonResource {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public DeliveryPersonResource(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    // Query: Get all delivery people
    @GetMapping
    public ResponseEntity<List<DeliveryPersonDTO>> getAllDeliveryPeople() {
        List<DeliveryPersonDTO> deliveryPeople = queryGateway.query(
                new GetAllDeliveryPeopleQuery(),
                ResponseTypes.multipleInstancesOf(DeliveryPersonDTO.class)
        ).join();
        return ResponseEntity.ok(deliveryPeople);
    }

    // Query: Get delivery person by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPersonDTO> getDeliveryPerson(@PathVariable String id) {
        DeliveryPersonDTO deliveryPerson = queryGateway.query(
                new GetDeliveryPersonQuery(id),
                DeliveryPersonDTO.class
        ).join();
        return ResponseEntity.ok(deliveryPerson);
    }

    // Command: Create delivery person
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createDeliveryPerson(@RequestBody @Valid DeliveryPersonDTO deliveryPersonDTO) {
        String id = UUID.randomUUID().toString();
        commandGateway.sendAndWait(new CreateDeliveryPersonCommand(id, deliveryPersonDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Command: Update delivery person
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDeliveryPerson(@PathVariable String id, @RequestBody @Valid DeliveryPersonDTO deliveryPersonDTO) {
        commandGateway.sendAndWait(new UpdateDeliveryPersonCommand(id, deliveryPersonDTO));
        return ResponseEntity.ok().build();
    }

    // Command: Delete delivery person
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDeliveryPerson(@PathVariable String id) {
        commandGateway.sendAndWait(new DeleteDeliveryPersonCommand(id));
        return ResponseEntity.noContent().build();
    }

}

