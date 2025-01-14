package com.example.coreapi.boutique.rayon.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RayonDeletedEvent {
    private String rayonId;
}