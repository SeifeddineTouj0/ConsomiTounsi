package com.jellali.forum.Query;


import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSujetsQueryHandler {
    private final SujetRepository sujetRepository;

    @Autowired
    public GetSujetsQueryHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public List<Sujet> handle(GetSujetsQuery query) {
        return sujetRepository.findAll();
    }
}

