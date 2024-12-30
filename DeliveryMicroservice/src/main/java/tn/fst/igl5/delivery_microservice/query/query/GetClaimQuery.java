package tn.fst.igl5.delivery_microservice.query.query;

import lombok.Getter;

@Getter
public class GetClaimQuery {
    private final String claimId;

    public GetClaimQuery(String claimId) {
        this.claimId = claimId;
    }    // Getter
 }
