package com.jellali.forum.Entities;


import java.time.LocalDateTime;

import com.jellali.forum.Command.Create.AddEvaluationCommand;
import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Event.EvaluationCreatedEvent;
import jakarta.persistence.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


//@Entity
//@Aggregate
//@Table(name = "evaluation")
//public class Evaluation {
//
//    public enum React {
//        LIKE, DISLIKE, LOVE, ANGRY, SAD, WOW // Includes emojis and other reactions
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @AggregateIdentifier
//    private Long id;
//
//    @Column(nullable = false)
//    private Long cibleId; // ID of the evaluated subject or comment
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private React react; // Reaction type: Like, Dislike, Emoji, etc.
//
//    @Column(nullable = true)
//    private Integer rating; // Optional numeric rating (0 to 10)
//
//    @Column(nullable = false)
//    private String auteur;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime dateCreation;
//
//    @ManyToOne
//    @JoinColumn(name = "sujet_id", nullable = true)
//    private Sujet sujet;
//
//    @ManyToOne
//    @JoinColumn(name = "commentaire_id", nullable = true)
//    private Commentaire commentaire;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getCibleId() {
//        return cibleId;
//    }
//
//    public void setCibleId(Long cibleId) {
//        this.cibleId = cibleId;
//    }
//
//    public React getReact() {
//        return react;
//    }
//
//    public void setReact(React react) {
//        this.react = react;
//    }
//
//    public Integer getRating() {
//        return rating;
//    }
//
//    public void setRating(Integer rating) {
//        this.rating = rating;
//    }
//
//    public String getAuteur() {
//        return auteur;
//    }
//
//    public void setAuteur(String auteur) {
//        this.auteur = auteur;
//    }
//
//    public LocalDateTime getDateCreation() {
//        return dateCreation;
//    }
//
//    public void setDateCreation(LocalDateTime dateCreation) {
//        this.dateCreation = dateCreation;
//    }
//
//    public Sujet getSujet() {
//        return sujet;
//    }
//
//    public void setSujet(Sujet sujet) {
//        this.sujet = sujet;
//    }
//
//    public Commentaire getCommentaire() {
//        return commentaire;
//    }
//
//    public void setCommentaire(Commentaire commentaire) {
//        this.commentaire = commentaire;
//    }
//
//
//    public Evaluation() {}
//
//    @CommandHandler
//    public Evaluation(AddEvaluationCommand command) {
//        // Handle the command and apply an event
//        this.id = command.getId();
//        this.cibleId = command.getCibleId();
//        this.react = command.getReact();
//        this.rating = command.getRating();
//        this.auteur = command.getAuteur();
//        this.dateCreation = LocalDateTime.now();
//
//        // Assuming that you are fetching Sujet/Commentaire based on IDs
//        // For actual aggregate creation, you should trigger events here if needed
//
//        // Applying an event for the evaluation creation
//        apply(new EvaluationCreatedEvent(this.id, this.cibleId, this.react, this.rating, this.auteur, this.sujet.getId(), this.commentaire.getId(), this.dateCreation));
//    }
//
//    @EventHandler
//    public void on(EvaluationCreatedEvent event) {
//        this.id = event.getId();
//        this.cibleId = event.getCibleId();
//        this.react = event.getReact();
//        this.rating = event.getRating();
//        this.auteur = event.getAuteur();
//        this.dateCreation = event.getDateCreation();
////        this.sujet = event.getSujet();
////        this.commentaire = event.getCommentaire();
//    }
//}

import jakarta.persistence.Entity;

@Entity
@Table(name = "evaluation")
public class Evaluation {

    public enum React {
        LIKE, DISLIKE, LOVE, ANGRY, SAD, WOW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cibleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private React react;

    @Column(nullable = true)
    private Integer rating;

    @Column(nullable = false)
    private String auteur;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "sujet_id", nullable = true)
    private Sujet sujet;

    @ManyToOne
    @JoinColumn(name = "commentaire_id", nullable = true)
    private Commentaire commentaire;

    // Getters and Setters
    // Constructor, Command handling logic can be omitted in entities class
}