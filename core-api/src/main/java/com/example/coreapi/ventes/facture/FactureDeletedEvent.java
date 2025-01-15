package com.example.coreapi.ventes.facture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureDeletedEvent {
        String factureId;
}
