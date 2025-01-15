//
//package com.jellali.forum.aggregates;
//
//import com.jellali.forum.Command.Create.CreateSujetCommand;
//import com.jellali.forum.Event.SujetCreatedEvent;
//import org.axonframework.commandhandling.CommandHandler;
//import org.axonframework.eventsourcing.EventSourcingHandler;
//import org.axonframework.modelling.command.AggregateIdentifier;
//import org.axonframework.modelling.command.AggregateLifecycle;
//import org.axonframework.spring.stereotype.Aggregate;
//
//import java.time.LocalDateTime;
//
//import static org.axonframework.modelling.command.AggregateLifecycle.apply;
//@Aggregate
//public class SujetAggregate {
//    @AggregateIdentifier
//    private Long id;
//    private String titre;
//    private String contenu;
//    private String auteur;
//    private LocalDateTime dateCreation;
//    private LocalDateTime lastInteractionDate;
//
//    // Default constructor needed by Axon
//    public SujetAggregate() {}
//
//    @CommandHandler
//    public SujetAggregate(CreateSujetCommand command) {
//        if (command.getId() == null) {
//            throw new IllegalArgumentException("ID cannot be null");
//        }
//        // Apply the event
//        AggregateLifecycle.apply(new SujetCreatedEvent(
//                command.getId(),
//                command.getTitre(),
//                command.getContenu(),
//                command.getAuteur(),
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        ));
//    }
//
//    @EventSourcingHandler
//    public void on(SujetCreatedEvent event) {
//        this.id = event.getId();
//        this.titre = event.getTitre();
//        this.contenu = event.getContenu();
//        this.auteur = event.getAuteur();
//        this.dateCreation = event.getDateCreation();
//        this.lastInteractionDate = event.getLastInteractionDate();
//    }
//}


package com.jellali.forum.aggregates;

import com.jellali.forum.Command.Create.CreateSujetCommand;
import com.jellali.forum.Event.SujetCreatedEvent;
import com.jellali.forum.Entities.Sujet;// Make sure this import points to your Sujet entity
import com.jellali.forum.Repository.SujetRepository; // Import your SujetRepository
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class SujetAggregate {

    @AggregateIdentifier
    private Long id;
    private String titre;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private LocalDateTime lastInteractionDate;

    private final SujetRepository sujetRepository;

    // Default constructor needed by Axon
    @Autowired
    public SujetAggregate(SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
    }

    @CommandHandler
    public SujetAggregate(CreateSujetCommand command, SujetRepository sujetRepository) {
        this.sujetRepository = sujetRepository;
        if (command.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        // Apply the event
        AggregateLifecycle.apply(new SujetCreatedEvent(
                command.getId(),
                command.getTitre(),
                command.getContenu(),
                command.getAuteur(),
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
    }

    @EventSourcingHandler
    public void on(SujetCreatedEvent event) {
        this.id = event.getId();
        this.titre = event.getTitre();
        this.contenu = event.getContenu();
        this.auteur = event.getAuteur();
        this.dateCreation = event.getDateCreation();
        this.lastInteractionDate = event.getLastInteractionDate();

        // Save the Sujet entity to the database
        Sujet sujet = new Sujet(this.id, this.titre, this.contenu, this.auteur, this.dateCreation, this.lastInteractionDate);
        sujetRepository.save(sujet);
    }
}
