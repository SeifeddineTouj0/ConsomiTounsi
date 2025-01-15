package com.jellali.forum.Command.Create;

import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddEvaluationCommandHandler {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public AddEvaluationCommandHandler(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public Evaluation handle(AddEvaluationCommand command) {
        // No need for repository call, since evaluation is now handled via CQRS and events
        return null;
    }
}
