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
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;
import tn.fst.igl5.delivery_microservice.service.ClaimService;


@RestController
@RequestMapping(value = "/api/claims", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClaimResource {

    private final ClaimService claimService;

    public ClaimResource(final ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<List<ClaimDTO>> getAllClaims() {
        return ResponseEntity.ok(claimService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimDTO> getClaim(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(claimService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createClaim(@RequestBody @Valid final ClaimDTO claimDTO) {
        final Long createdId = claimService.create(claimDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateClaim(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ClaimDTO claimDTO) {
        claimService.update(id, claimDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClaim(@PathVariable(name = "id") final Long id) {
        claimService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
