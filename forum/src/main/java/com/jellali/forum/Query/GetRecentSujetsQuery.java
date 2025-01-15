package com.jellali.forum.Query;

public class GetRecentSujetsQuery {
    private final int limit;

    public GetRecentSujetsQuery(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}

