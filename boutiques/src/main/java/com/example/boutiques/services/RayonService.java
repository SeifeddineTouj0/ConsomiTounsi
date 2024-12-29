package com.example.boutiques.services;

import com.example.boutiques.entities.Rayon;
import com.example.boutiques.repositories.RayonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RayonService {

    private final RayonRepository rayonRepository;

    public RayonService(RayonRepository rayonRepository) {
        this.rayonRepository = rayonRepository;
    }

    public List<Rayon> getAllRayons() {
        return rayonRepository.findAll();
    }

    public Optional<Rayon> getRayonById(String id) {
        return rayonRepository.findById(id);
    }

    public Rayon saveRayon(Rayon rayon) {
        return rayonRepository.save(rayon);
    }

    public void deleteRayon(String id) {
        rayonRepository.deleteById(id);
    }
}
