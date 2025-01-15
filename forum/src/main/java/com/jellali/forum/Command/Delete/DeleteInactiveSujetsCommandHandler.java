package com.jellali.forum.Command.Delete;

import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DeleteInactiveSujetsCommandHandler {
    private final SujetRepository sujetRepository;

    @Autowired
    public DeleteInactiveSujetsCommandHandler(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    public void handle(DeleteInactiveSujetsCommand command) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);
        List<Sujet> inactiveSujets = sujetRepository.findInactiveSince(cutoffDate);
        inactiveSujets.forEach(sujetRepository::delete);
    }
}
