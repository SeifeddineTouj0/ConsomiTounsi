package com.example.boutiques.handlers;

import com.example.boutiques.entities.Rayon;
import com.example.boutiques.repositories.RayonRepository;
import com.example.coreapi.boutique.rayon.events.ProductAssignedToRayonEvent;
import com.example.coreapi.boutique.rayon.events.RayonCreatedEvent;
import com.example.coreapi.boutique.rayon.events.RayonDeletedEvent;
import com.example.coreapi.boutique.rayon.events.RayonUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RayonEventHandler {

    private final RayonRepository rayonRepository;

    @Autowired
    public RayonEventHandler(RayonRepository rayonRepository) {
        this.rayonRepository = rayonRepository;
    }

    @EventHandler
    public void on(RayonCreatedEvent event) {
        Rayon rayon = new Rayon();
        rayon.setId(event.getRayonId());
        rayon.setName(event.getName());
        rayon.setType(event.getType());
        rayon.setProductIds(event.getProductIds());
        rayon.setPosition(event.getPosition());

        rayonRepository.save(rayon);
        System.out.println("Rayon created and saved to DB: " + rayon.getId());
    }

    @EventHandler
    public void on(RayonUpdatedEvent event) {
        Optional<Rayon> optionalRayon = rayonRepository.findById(event.getRayonId());
        if (optionalRayon.isPresent()) {
            Rayon rayon = optionalRayon.get();
            rayon.setName(event.getName());
            rayon.setType(event.getType());
            rayon.setProductIds(event.getProductIds());
            rayon.setPosition(event.getPosition());

            rayonRepository.save(rayon);
            System.out.println("Rayon updated in DB: " + rayon.getId());
        } else {
            System.err.println("Rayon with ID " + event.getRayonId() + " not found for update.");
        }
    }

    @EventHandler
    public void on(RayonDeletedEvent event) {
        rayonRepository.deleteById(event.getRayonId());
        System.out.println("Rayon deleted from DB: " + event.getRayonId());
    }

    @EventHandler
    public void on(ProductAssignedToRayonEvent event) {
        Optional<Rayon> optionalRayon = rayonRepository.findById(event.getRayonId());
        if (optionalRayon.isPresent()) {
            Rayon rayon = optionalRayon.get();
            rayon.getProductIds().add(event.getProductId());

            rayonRepository.save(rayon);
            System.out.println("Product assigned to Rayon in DB: Rayon ID " + rayon.getId() + ", Product ID " + event.getProductId());
        } else {
            System.err.println("Rayon with ID " + event.getRayonId() + " not found for product assignment.");
        }
    }
}