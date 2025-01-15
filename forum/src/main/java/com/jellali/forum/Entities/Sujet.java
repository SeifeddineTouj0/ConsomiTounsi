package com.jellali.forum.Entities;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
//
//@Entity
//@Aggregate
//@Table(name = "sujets")
//public class Sujet {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @AggregateIdentifier
//    private Long id;
//
//    @Column(nullable = false)
//    private String titre;
//
//    @Column(nullable = false, length = 5000)
//    private String contenu;
//
//    @Column(nullable = false)
//    private String auteur;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime dateCreation;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime lastInteractionDate;
//
//    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Commentaire> commentaires;
//
//    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Evaluation> evaluations;
//
//
//    // Axon command handler for CreateSujetCommand
//    @CommandHandler
//    public Sujet(CreateSujetCommand command) {
//        this.titre = command.getTitre();
//        this.contenu = command.getContenu();
//        this.auteur = command.getAuteur();
//        this.dateCreation = LocalDateTime.now();
//        this.lastInteractionDate = LocalDateTime.now();
//    }
//
//    public Sujet(Long id, String titre, String contenu, String auteur, LocalDateTime dateCreation, LocalDateTime lastInteractionDate) {
//
//    }
//
//    public Sujet() {
//
//    }
//
//    // Event handler for handling events related to this aggregate
//    @EventHandler
//    public void on(SujetCreatedEvent event) {
//        this.id = event.getId();
//        this.titre = event.getTitre();
//        this.contenu = event.getContenu();
//        this.auteur = event.getAuteur();
//        this.dateCreation = event.getDateCreation();
//        this.lastInteractionDate = event.getLastInteractionDate();
//    }
//
//    @CommandHandler
//    public void handle(DeleteSujetCommand command) {
//        apply(new SujetDeletedEvent(command.getId()));
//    }
//
//    @CommandHandler
//    public void handle(DeleteInactiveSujetsCommand command) {
//        apply(new InactiveSujetsDeletedEvent(command.getCutoffDate()));
//    }
//
//    @EventSourcingHandler
//    public void on(SujetDeletedEvent event) {
//        this.id = event.getId();
//    }
//
//    @EventSourcingHandler
//    public void on(InactiveSujetsDeletedEvent event) {
//        this.lastInteractionDate = event.getCutoffDate();
//    }
//    // Getters and Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getContenu() {
//        return contenu;
//    }
//
//    public String getAuteur() {
//        return auteur;
//    }
//
//    public LocalDateTime getDateCreation() {
//        return dateCreation;
//    }
//
//    public List<Commentaire> getCommentaires() {
//        return commentaires;
//    }
//
//    public String getTitre() {
//        return titre;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setTitre(String titre) {
//        this.titre = titre;
//    }
//
//    public void setContenu(String contenu) {
//        this.contenu = contenu;
//    }
//
//    public void setAuteur(String auteur) {
//        this.auteur = auteur;
//    }
//
//    public void setDateCreation(LocalDateTime dateCreation) {
//        this.dateCreation = dateCreation;
//    }
//
//    public void setCommentaires(List<Commentaire> commentaires) {
//        this.commentaires = commentaires;
//    }
//
//    public void setLastInteractionDate(LocalDateTime lastInteractionDate) {
//        this.lastInteractionDate = lastInteractionDate;
//    }
//}



@Entity
@Table(name = "sujets")
public class Sujet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, length = 5000)
    private String contenu;

    @Column(nullable = false)
    private String auteur;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime lastInteractionDate;

    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations;

    public Sujet(Long id, String titre, String contenu, String auteur, LocalDateTime dateCreation, LocalDateTime lastInteractionDate) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
        this.lastInteractionDate = lastInteractionDate;
        this.commentaires = commentaires;
        this.evaluations = evaluations;
    }

    public Sujet(int i, String titre, String contenu, String auteur, LocalDateTime now, LocalDateTime nowed) {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setLastInteractionDate(LocalDateTime lastInteractionDate) {
        this.lastInteractionDate = lastInteractionDate;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public String getTitre() {
        return titre;
    }

    // Getters and setters
}