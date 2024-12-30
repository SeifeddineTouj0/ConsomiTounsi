package tn.fst.igl5.delivery_microservice.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

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
import tn.fst.igl5.delivery_microservice.model.DeliveryDTO;
import tn.fst.igl5.delivery_microservice.service.DeliveryService;

@RestController
@RequestMapping(value = "/api/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryResource {
    private final DeliveryService deliveryService;

    public DeliveryResource(final DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDelivery(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(deliveryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDelivery(@RequestBody @Valid final DeliveryDTO deliveryDTO) {
        final Long createdId = deliveryService.create(deliveryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDelivery(@PathVariable(name = "id") final Long id, @RequestBody @Valid final DeliveryDTO deliveryDTO) {
        deliveryService.update(id, deliveryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDelivery(@PathVariable(name = "id") final Long id) {
        deliveryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
