package com.jellali.forum.Query;

import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRecentSujetsQueryHandler {

    private final SujetRepository sujetRepository;

    // Constructor injection (preferred)
    @Autowired
    public GetRecentSujetsQueryHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public List<Sujet> handle(GetRecentSujetsQuery query) {
        // Use PageRequest to limit the results to the top 3 most recent sujets
        Pageable pageable = PageRequest.of(0, 3);
        return sujetRepository.findTopSujetsByDateCreationDesc(pageable);
    }
}
