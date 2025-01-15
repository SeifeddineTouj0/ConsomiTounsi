package com.jellali.forum.aggregates;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import com.jellali.forum.Event.CommentCreatedEvent;
import java.time.LocalDateTime;

@Aggregate
public class CommentaireAggregate {
    @AggregateIdentifier
    private Long id;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;

    // Command handler for creating a comment
//    @CommandHandler
//    public CommentaireAggregate(CommentCreatedEvent event) {
//        // Event handling logic - this should handle the event that is passed when the comment is created
//        this.id = event.getId();
//        this.contenu = event.getContenu();
//        this.auteur = event.getAuteur();
//        this.dateCreation = event.getDateCreation();  // Use the date from the event
//    }

    // Event sourcing handler that restores the state of the aggregate based on events
    @EventSourcingHandler
    public void on(CommentCreatedEvent event) {
        // This method is automatically called when the event is applied, restoring the aggregate's state
        this.id = event.getId();
        this.contenu = event.getContenu();
        this.auteur = event.getAuteur();
        this.dateCreation = event.getDateCreation();
    }
}
