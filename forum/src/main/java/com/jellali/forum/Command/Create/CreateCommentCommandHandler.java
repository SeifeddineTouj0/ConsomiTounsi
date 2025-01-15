package com.jellali.forum.Command.Create;

import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Repository.CommentaireRepository;  // Use JPA repository
import com.jellali.forum.Repository.SujetRepository;
import com.jellali.forum.Event.CommentCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCommentCommandHandler {

    private final SujetRepository sujetRepository;
    private final CommentaireRepository commentaireRepository; // JPA repository for Commentaire

    public CreateCommentCommandHandler(SujetRepository sujetRepository, CommentaireRepository commentaireRepository) {
        this.sujetRepository = sujetRepository;
        this.commentaireRepository = commentaireRepository;
    }

    @CommandHandler
    public void handle(CreateCommentCommand command) throws Exception {
        Sujet sujet = sujetRepository.findById(command.getSujetId())
                .orElseThrow(() -> new IllegalArgumentException("Sujet non trouvé"));

        Commentaire commentaire = Commentaire.create(
                command.getContenu(),
                command.getAuteur(),
                sujet,
                LocalDateTime.now()
        );

        // Save the commentaire using the JPA repository
        commentaireRepository.save(commentaire);

        // Optionally, you can publish an event (if you're still using events)
        // eventGateway.publish(new CommentCreatedEvent(commentaire));
    }
}