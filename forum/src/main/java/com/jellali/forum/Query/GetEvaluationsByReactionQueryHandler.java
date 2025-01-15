package com.jellali.forum.Query;


import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Repository.EvaluationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetEvaluationsByReactionQueryHandler {
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public GetEvaluationsByReactionQueryHandler(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @QueryHandler
    public List<Evaluation> handle(GetEvaluationsByReactionQuery query) {
        return evaluationRepository.findByReact(query.getReact());
    }
}
