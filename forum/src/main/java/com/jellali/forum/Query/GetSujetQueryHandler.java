package com.jellali.forum.Query;

import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSujetQueryHandler {

    private final SujetRepository sujetRepository;

    @Autowired
    public GetSujetQueryHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public Sujet handle(GetSujetQuery query) {
        return sujetRepository.findById(query.getId())
                .orElseThrow(() -> new RuntimeException("Sujet non trouvé"));
    }
}

