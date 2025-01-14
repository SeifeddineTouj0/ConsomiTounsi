package com.example.coreapi.boutique.rayon.events;

import com.example.coreapi.boutique.rayon.enumerations.RayonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RayonUpdatedEvent {
    private String rayonId;
    private String name;
    private RayonType type;
    private List<String> productIds;
    private String position;
}
