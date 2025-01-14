package com.consommitounsi.cagnottes.cagnottes.projections;

import com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte;
import com.consommitounsi.cagnottes.cagnottes.events.CagnotteCreeeEvent;
import com.consommitounsi.cagnottes.cagnottes.events.DonAjouteEvent;
import com.consommitounsi.cagnottes.cagnottes.repositories.CagnotteRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class CagnotteProjection {

    private final CagnotteRepository cagnotteRepository;

    public CagnotteProjection(CagnotteRepository cagnotteRepository) {
        this.cagnotteRepository = cagnotteRepository;
    }

    // pour faire l'update du read db
    @EventHandler
    public void on(CagnotteCreeeEvent event) {
        cagnotteRepository.save(new Cagnotte(event.getCagnotteId(), event.getNom(), event.getDescription(), event.getMontantCible(), 0.0));
    }

    @EventHandler
    public void on(DonAjouteEvent event) {
        Cagnotte cagnotte = cagnotteRepository.findById(event.getCagnotteId()).orElse(null);
        if (cagnotte != null)
            cagnotte.setMontantCollecte(cagnotte.getMontantCollecte() + event.getMontant());
    }

    public Cagnotte getCagnotte(String cagnotteId) {
        return cagnotteRepository.findById(cagnotteId).orElse(null);
    }
}
