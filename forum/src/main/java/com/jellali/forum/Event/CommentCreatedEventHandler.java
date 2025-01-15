package com.jellali.forum.Event;

import com.jellali.forum.Event.CommentCreatedEvent;
import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Repository.CommentaireRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentCreatedEventHandler {
    private final CommentaireRepository commentaireRepository;

    @Autowired
    public CommentCreatedEventHandler(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    @EventHandler
    public void on(CommentCreatedEvent event) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(event.getId());
        commentaire.setContenu(event.getContenu());
        commentaire.setAuteur(event.getAuteur());
        // Get the subject from the repository or create the logic to resolve the subject by id
        // commentaire.setSujet(...);
        commentaireRepository.save(commentaire);
    }
}

