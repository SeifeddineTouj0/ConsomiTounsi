package com.jellali.forum.Query;

import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InactiveSujetsQueryHandler {

    private final SujetRepository sujetRepository;

    @Autowired
    public InactiveSujetsQueryHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public List<Sujet> handle(LocalDateTime cutoffDate) {
        return sujetRepository.findInactiveSince(cutoffDate);
    }
}

