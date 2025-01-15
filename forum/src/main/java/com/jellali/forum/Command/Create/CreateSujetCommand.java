package com.jellali.forum.Command.Create;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateSujetCommand {

    @TargetAggregateIdentifier
    private Long id;  // This should uniquely identify the aggregate

    private String titre;
    private String contenu;
    private String auteur;

    public CreateSujetCommand(Long id, String titre, String contenu, String auteur) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public String getAuteur() {
        return auteur;
    }
}
