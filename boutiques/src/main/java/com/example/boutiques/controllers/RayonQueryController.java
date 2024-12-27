package com.example.boutiques.controllers;

import com.example.boutiques.entities.Rayon;
import com.example.coreapi.boutique.rayon.queries.GetAllRayonsQuery;
import com.example.coreapi.boutique.rayon.queries.GetRayonQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/rayons/queries")
public class RayonQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Rayon>> getAllRayons() {
        return queryGateway.query(new GetAllRayonsQuery(), ResponseTypes.multipleInstancesOf(Rayon.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Rayon> getRayon(@PathVariable String id) {
        return queryGateway.query(new GetRayonQuery(id), ResponseTypes.instanceOf(Rayon.class));
    }
}
