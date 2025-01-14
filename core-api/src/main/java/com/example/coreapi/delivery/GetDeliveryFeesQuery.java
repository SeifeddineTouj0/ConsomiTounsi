package com.example.coreapi.delivery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GetDeliveryFeesQuery {



    OrderDetailsDTO order;

    @JsonCreator
    public GetDeliveryFeesQuery(@JsonProperty("order") OrderDetailsDTO order) {
        this.order = order;
    }
}
