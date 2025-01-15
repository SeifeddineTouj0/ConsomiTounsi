package com.jellali.forum.Query;

import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Repository.EvaluationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetEvaluationsByRatingQueryHandler {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public GetEvaluationsByRatingQueryHandler(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @QueryHandler
    public List<Evaluation> handle(GetEvaluationsByRatingQuery query) {
        return evaluationRepository.findByRatingBetween(query.getMinRating(), query.getMaxRating());
    }
}
