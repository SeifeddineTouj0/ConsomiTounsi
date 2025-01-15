package com.jellali.forum.Command.Update;

import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateSujetCommandHandler {
    private final SujetRepository sujetRepository;

    @Autowired
    public UpdateSujetCommandHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public Sujet handle(UpdateSujetCommand command) {
        Sujet sujet = sujetRepository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Sujet non trouvé"));

        sujet.setTitre(command.getTitre());
        sujet.setContenu(command.getContenu());
        sujet.setLastInteractionDate(LocalDateTime.now());

        return sujetRepository.save(sujet);
    }
}

