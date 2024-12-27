package com.example.boutiques.projections;

import com.example.boutiques.entities.Rayon;
import com.example.boutiques.repositories.RayonRepository;
import com.example.coreapi.boutique.rayon.queries.GetAllRayonsQuery;
import com.example.coreapi.boutique.rayon.queries.GetRayonQuery;
import com.example.coreapi.boutique.rayon.events.ProductAssignedToRayonEvent;
import com.example.coreapi.boutique.rayon.events.RayonCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class RayonProjection {

    @Autowired
    private RayonRepository rayonRepository;

    @QueryHandler
    public CompletableFuture<List<Rayon>> handle(GetAllRayonsQuery query) {
        return CompletableFuture.completedFuture(rayonRepository.findAll());
    }

    @QueryHandler
    public CompletableFuture<Rayon> handle(GetRayonQuery query) {
        return CompletableFuture.completedFuture(
                rayonRepository.findById(query.getRayonId()).orElse(null)
        );
    }
}
