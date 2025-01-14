package com.consommitounsi.cagnottes.cagnottes.projections;

import com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte;
import com.consommitounsi.cagnottes.cagnottes.events.CagnotteCreeeEvent;
import com.consommitounsi.cagnottes.cagnottes.events.CagnotteSupprimeeEvent;
import com.consommitounsi.cagnottes.cagnottes.events.DonAjouteEvent;
import com.consommitounsi.cagnottes.cagnottes.queries.GetAllCagnottesQuery;
import com.consommitounsi.cagnottes.cagnottes.queries.GetCagnotteByIdQuery;
import com.consommitounsi.cagnottes.cagnottes.repositories.CagnotteRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @EventHandler
    public void on(CagnotteSupprimeeEvent event) {
        cagnotteRepository.deleteById(event.getCagnotteId());
    }

    @QueryHandler
    public Cagnotte handle(GetCagnotteByIdQuery query) {
        return cagnotteRepository.findById(query.getCagnotteId())
                .orElseThrow(() -> new RuntimeException("Cagnotte not found for ID: " + query.getCagnotteId()));
    }

    @QueryHandler
    public List<Cagnotte> handle(GetAllCagnottesQuery query) {
        return cagnotteRepository.findAll();
    }
}
