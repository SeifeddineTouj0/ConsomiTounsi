package com.jellali.forum.Repository;

import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Entities.Sujet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation , Long> {
    Evaluation save(Evaluation evaluation);
    List<Evaluation> findByReact(Evaluation.React react);

    List<Evaluation> findByRatingBetween(int minRating, int maxRating);
}
