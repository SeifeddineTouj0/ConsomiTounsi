package tn.fst.igl5.delivery_microservice.rest;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.fst.igl5.delivery_microservice.command.command.CreateClaimCommand;
import tn.fst.igl5.delivery_microservice.command.command.DeleteClaimCommand;
import tn.fst.igl5.delivery_microservice.command.command.UpdateClaimCommand;
import tn.fst.igl5.delivery_microservice.domain.Claim;
import tn.fst.igl5.delivery_microservice.model.ClaimDTO;
import tn.fst.igl5.delivery_microservice.query.query.GetAllClaimsQuery;
import tn.fst.igl5.delivery_microservice.query.query.GetClaimQuery;
import tn.fst.igl5.delivery_microservice.service.ClaimService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/claims")
public class ClaimResource {
    private final ClaimService claimService;
    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    public ClaimResource(ClaimService claimService, QueryGateway queryGateway, CommandGateway commandGateway) {
        this.claimService = claimService;
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }

    @GetMapping
    public ResponseEntity<List<ClaimDTO>> getAllClaims() {
        List<ClaimDTO> claims = queryGateway.query(new GetAllClaimsQuery(), ResponseTypes.multipleInstancesOf(ClaimDTO.class)).join();
        return ResponseEntity.ok(claims);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimDTO> getClaim(@PathVariable String id) {
        ClaimDTO claim = queryGateway.query(new GetClaimQuery(id), ClaimDTO.class).join();
        return ResponseEntity.ok(claim);
    }

    @PostMapping
    public ResponseEntity<String> createClaim(@RequestBody ClaimDTO claimDTO) {
        String id = UUID.randomUUID().toString();
        commandGateway.send(new CreateClaimCommand(id, claimDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClaim(@PathVariable String id, @RequestBody ClaimDTO claimDTO) {
        commandGateway.send(new UpdateClaimCommand(id, claimDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable String id) {
        commandGateway.send(new DeleteClaimCommand(id));
        return ResponseEntity.noContent().build();
    }

}
