package com.jellali.forum.Event;

import com.jellali.forum.Entities.Evaluation;

import java.time.LocalDateTime;

public class EvaluationCreatedEvent {
    private Long id;
    private Long cibleId;
    private Evaluation.React react;
    private Integer rating;
    private String auteur;
    private Long sujetId;
    private Long commentaireId;
    private LocalDateTime dateCreation;

    public EvaluationCreatedEvent(Long id, Long cibleId, Evaluation.React react, Integer rating, String auteur, LocalDateTime dateCreation) {
        this.id = id;
        this.cibleId = cibleId;
        this.react = react;
        this.rating = rating;
        this.auteur = auteur;
        this.sujetId = sujetId;
        this.commentaireId = commentaireId;
        this.dateCreation = dateCreation;
    }

    public EvaluationCreatedEvent(Long id, Long cibleId, String react, Integer rating, String auteur, LocalDateTime dateCreation) {
    }

    public Long getId() {
        return id;
    }

    public Long getCibleId() {
        return cibleId;
    }

    public Evaluation.React getReact() {
        return react;
    }

    public Integer getRating() {
        return rating;
    }

    public String getAuteur() {
        return auteur;
    }

    public Long getSujetId() {
        return sujetId;
    }

    public Long getCommentaireId() {
        return commentaireId;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
// Getters and setters
}

