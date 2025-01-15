package com.jellali.forum.projection;

import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Event.CommentCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jellali.forum.Repository.CommentaireRepository;

import java.util.List;

@Component
public class CommentaireProjection {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @EventHandler
    public void on(CommentCreatedEvent event) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(event.getId());
        commentaire.setContenu(event.getContenu());
        commentaire.setAuteur(event.getAuteur());
        commentaire.setDateCreation(event.getDateCreation());
        // Add sujet assignment if required
        commentaireRepository.save(commentaire);
    }

    public List<Commentaire> getAllComments() {
        return commentaireRepository.findAll();
    }

    public List<Commentaire> getMostRelevantComments() {
        // Add logic to fetch the most relevant comments
        return commentaireRepository.findTop10ByOrderByDateCreationDesc();
    }
}
