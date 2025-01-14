package com.consommitounsi.cagnottes.cagnottes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cagnotte {

    @Id
    private String cagnotteId;
    private String nom;
    private String description;
    private double montantCible;
    private double montantCollecte;

    public Cagnotte(String cagnotteId, String nom, String description, double montantCible, double montantCollecte) {
        this.cagnotteId = cagnotteId;
        this.nom = nom;
        this.description = description;
        this.montantCible = montantCible;
        this.montantCollecte = montantCollecte;
    }

    public Cagnotte() {
    }


    // Constructeurs, getters, setters
}

