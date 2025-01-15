package com.jellali.forum.Query;

import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jellali.forum.Entities.Sujet;

import java.util.Optional;



    @Service
    public class TestRedundantSujetQueryHandler {

        private final SujetRepository sujetRepository;

        @Autowired
        public TestRedundantSujetQueryHandler(SujetRepository sujetRepository) {
            this.sujetRepository = sujetRepository;
        }

        public boolean handle(TestRedundantSujetQuery query) {
            // Use the repository to check if a subject with the same title exists
            Optional<Sujet> existingSujet = sujetRepository.findByTitre(query.getTitre());
            return existingSujet.isPresent(); // True if redundant, False otherwise
        }
    }

