package com.jellali.forum.aggregates;

import com.jellali.forum.Command.Create.AddEvaluationCommand;
import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Event.EvaluationCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

@Aggregate
public class EvaluationAggregate {
    @AggregateIdentifier
    private Long id;
    private Long cibleId;
    private Evaluation.React react;
    private Integer rating;
    private String auteur;
    private LocalDateTime dateCreation;

//    @CommandHandler
//    public EvaluationAggregate(AddEvaluationCommand command) {
//        this.id = command.getId();
//        this.cibleId = command.getCibleId();
//        this.react = command.getReact();
//        this.rating = command.getRating();
//        this.auteur = command.getAuteur();
//        this.dateCreation = LocalDateTime.now();
//        AggregateLifecycle.apply(new EvaluationCreatedEvent(id, cibleId, react, rating, auteur, dateCreation));
//    }

    @EventSourcingHandler
    public void on(EvaluationCreatedEvent event) {
        this.id = event.getId();
        this.cibleId = event.getCibleId();
        this.react = event.getReact();
        this.rating = event.getRating();
        this.auteur = event.getAuteur();
        this.dateCreation = event.getDateCreation();
    }
}
