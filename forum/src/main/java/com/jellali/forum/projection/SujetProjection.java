package com.jellali.forum.projection;

import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Event.InactiveSujetsDeletedEvent;
import com.jellali.forum.Event.SujetCreatedEvent;
import com.jellali.forum.Event.SujetDeletedEvent;
import com.jellali.forum.Query.GetRecentSujetsQuery;
import com.jellali.forum.Query.GetSujetQuery;
import com.jellali.forum.Query.GetSujetsQuery;
import com.jellali.forum.Query.TestRedundantSujetQuery;
import com.jellali.forum.Repository.SujetRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SujetProjection {

    private final  SujetRepository sujetRepository;
    private final Map<Long, Sujet> sujetStore = new ConcurrentHashMap<>();

    @Autowired
    public SujetProjection(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }


    @EventHandler
    public void on(SujetCreatedEvent event) {
        Sujet sujet = new Sujet(
                event.getId(),
                event.getTitre(),
                event.getContenu(),
                event.getAuteur(),
                event.getDateCreation(),
                event.getLastInteractionDate()
        );
        if (sujet == null) {
            System.err.println("Sujet object is null!");
        }
        sujetRepository.save(sujet);
    }


    @EventHandler
    public void on(SujetDeletedEvent event) {
        sujetRepository.deleteById(event.getId());
    }

    @EventHandler
    public void on(InactiveSujetsDeletedEvent event) {
        sujetRepository.deleteAllByLastInteractionDateBefore(event.getCutoffDate());
    }


    @QueryHandler
    public List<Sujet> handle(GetSujetsQuery query) {
        return new ArrayList<>(sujetStore.values());
    }

    @QueryHandler
    public List<Sujet> handle(GetRecentSujetsQuery query) {
        return sujetStore.values().stream()
                .sorted(Comparator.comparing(Sujet::getDateCreation).reversed())
                .limit(query.getLimit())
                .toList();
    }

    @QueryHandler
    public Sujet handle(GetSujetQuery query) {
        return Optional.ofNullable(sujetStore.get(query.getId()))
                .orElseThrow(() -> new RuntimeException("Sujet not found"));
    }

    @QueryHandler
    public boolean handle(TestRedundantSujetQuery query) {
        return sujetStore.values().stream()
                .anyMatch(sujet -> sujet.getTitre().equals(query.getTitre()));
    }
}
