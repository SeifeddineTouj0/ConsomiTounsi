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
import tn.fst.igl5.delivery_microservice.model.DeliveryPersonDTO;
import tn.fst.igl5.delivery_microservice.service.DeliveryPersonService;
import tn.fst.igl5.delivery_microservice.util.ReferencedException;
import tn.fst.igl5.delivery_microservice.util.ReferencedWarning;

@RestController
@RequestMapping(value = "/api/deliveryPeople", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryPersonResource {
    private final DeliveryPersonService deliveryPersonService;

    public DeliveryPersonResource(final DeliveryPersonService deliveryPersonService) {
        this.deliveryPersonService = deliveryPersonService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryPersonDTO>> getAllDeliveryPeople() {
        return ResponseEntity.ok(deliveryPersonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPersonDTO> getDeliveryPerson(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(deliveryPersonService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDeliveryPerson(@RequestBody @Valid final DeliveryPersonDTO deliveryPersonDTO) {
        final Long createdId = deliveryPersonService.create(deliveryPersonDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDeliveryPerson(@PathVariable(name = "id") final Long id, @RequestBody @Valid final DeliveryPersonDTO deliveryPersonDTO) {
        deliveryPersonService.update(id, deliveryPersonDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDeliveryPerson(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = deliveryPersonService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        deliveryPersonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
