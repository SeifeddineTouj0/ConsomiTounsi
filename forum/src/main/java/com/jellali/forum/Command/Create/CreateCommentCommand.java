package com.jellali.forum.Command.Create;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateCommentCommand {
    @TargetAggregateIdentifier
    private Long id;
    private String contenu;
    private String auteur;
    private Long sujetId;
    public String getContenu() {
        return contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public Long getSujetId() {
        return sujetId;
    }
}
