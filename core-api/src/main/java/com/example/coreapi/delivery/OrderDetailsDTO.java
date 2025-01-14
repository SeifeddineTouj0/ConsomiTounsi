package com.example.coreapi.delivery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
public class OrderDetailsDTO {
    private final Double targetLat;
    private final Double targetLng;
    private final List<Double> weights;

    @JsonCreator
    public OrderDetailsDTO(
            @JsonProperty("targetLat") Double targetLat,
            @JsonProperty("targetLng") Double targetLng,
            @JsonProperty("weights") List<Double> weights
    ) {
        this.targetLat = targetLat;
        this.targetLng = targetLng;
        this.weights = weights;
    }

    public Double getTargetLat() {
        return targetLat;
    }

    public Double getTargetLng() {
        return targetLng;
    }

    public List<Double> getWeights() {
        return weights;
    }
}
