package com.jellali.forum.Event;

import java.time.LocalDateTime;

public class SujetCreatedEvent {
    private Long id;
    private String titre;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private LocalDateTime lastInteractionDate;

    // Constructor, Getters

    public SujetCreatedEvent(Long id, String titre, String contenu, String auteur, LocalDateTime dateCreation, LocalDateTime lastInteractionDate) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
        this.lastInteractionDate = lastInteractionDate;
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getLastInteractionDate() {
        return lastInteractionDate;
    }
}

