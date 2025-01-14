package tn.fst.igl5.delivery_microservice.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.coreapi.delivery.CreateClaimCommand;
import com.example.coreapi.delivery.command.DeleteClaimCommand;
import com.example.coreapi.delivery.command.UpdateClaimCommand;
import com.example.coreapi.delivery.ClaimDTO;
import com.example.coreapi.delivery.GetAllClaimsQuery;
import com.example.coreapi.delivery.GetClaimQuery;
import tn.fst.igl5.delivery_microservice.service.ClaimService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<ResponseEntity<List<ClaimDTO>>> getAllClaims() {
        return queryGateway.query(new GetAllClaimsQuery(), ResponseTypes.multipleInstancesOf(ClaimDTO.class)).thenApply(claimDTOList -> ResponseEntity.ok(claimDTOList));

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
