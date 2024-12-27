package com.example.boutiques.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.boutiques.entities.Rayon;
public interface RayonRepository extends JpaRepository<Rayon, String> {
}
