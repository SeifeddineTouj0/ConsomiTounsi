package com.jellali.forum.projection;

import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Query.GetEvaluationsByRatingQuery;
import com.jellali.forum.Query.GetEvaluationsByReactionQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jellali.forum.Repository.EvaluationRepository;

import java.util.List;

@Component
public class EvaluationProjection {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationProjection(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @QueryHandler
    public List<Evaluation> handle(GetEvaluationsByRatingQuery query) {
        return evaluationRepository.findByRatingBetween(query.getMinRating(), query.getMaxRating());
    }

    @QueryHandler
    public List<Evaluation> handle(GetEvaluationsByReactionQuery query) {
        return evaluationRepository.findByReact(query.getReact());
    }
}
