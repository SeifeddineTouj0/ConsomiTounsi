package com.jellali.forum.Command.Delete;


import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteSujetCommandHandler {
    private final SujetRepository sujetRepository;

    @Autowired
    public DeleteSujetCommandHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public void handle(DeleteSujetCommand command) {
        Sujet sujet = sujetRepository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Sujet non trouvé"));
        sujetRepository.delete(sujet);
    }
}

