package com.jellali.forum.Query;

import com.jellali.forum.Entities.Evaluation;

public class GetEvaluationsByReactionQuery {
    private final Evaluation.React react;

    public GetEvaluationsByReactionQuery(Evaluation.React react) {
        this.react = react;
    }

    public Evaluation.React getReact() {
        return react;
    }
}
