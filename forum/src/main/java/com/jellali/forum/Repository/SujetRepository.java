package com.jellali.forum.Repository;

import com.jellali.forum.Entities.Sujet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SujetRepository extends JpaRepository<Sujet, Long> {
    List<Sujet> findByAuteur(String auteur);
    Optional<Sujet> findByTitre(String titre);

    // Requête pour récupérer les sujets inactifs depuis une date de coupure (cutoffDate)
    @Query("SELECT s FROM Sujet s WHERE s.lastInteractionDate < :cutoffDate")
    List<Sujet> findInactiveSince(LocalDateTime cutoffDate);

    // Fetch the top 3 most recent sujets (fixed limit version)
    List<Sujet> findTop3ByOrderByDateCreationDesc();

    // Alternatively, you can use a custom @Query for dynamic limits:
    @Query("SELECT s FROM Sujet s ORDER BY s.dateCreation DESC")
    List<Sujet> findTopSujetsByDateCreationDesc(org.springframework.data.domain.Pageable pageable);

    void deleteAllByLastInteractionDateBefore(LocalDateTime cutoffDate);
}
