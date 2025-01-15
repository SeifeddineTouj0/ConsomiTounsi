package com.jellali.forum.Query;

public class GetEvaluationsByRatingQuery {
    private final int minRating;
    private final int maxRating;

    public GetEvaluationsByRatingQuery(int minRating, int maxRating) {
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public int getMinRating() {
        return minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }
}
