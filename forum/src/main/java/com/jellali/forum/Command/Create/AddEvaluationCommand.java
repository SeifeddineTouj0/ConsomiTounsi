package com.jellali.forum.Command.Create;

import com.jellali.forum.Entities.Evaluation;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class AddEvaluationCommand {
    @TargetAggregateIdentifier
    private Long id; // This is the identifier for the evaluation aggregate
    private Long cibleId;
    private Evaluation.React react;
    private Integer rating; // Optional field
    private String auteur;
    private Long sujetId; // Optional
    private Long commentaireId; // Optional

    // Constructor, getters and setters
    public AddEvaluationCommand(Long id, Long cibleId, Evaluation.React react, Integer rating, String auteur, Long sujetId, Long commentaireId) {
        this.id = id;
        this.cibleId = cibleId;
        this.react = react;
        this.rating = rating;
        this.auteur = auteur;
        this.sujetId = sujetId;
        this.commentaireId = commentaireId;
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
}
