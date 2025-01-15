package com.jellali.forum.Event;

import java.time.LocalDateTime;

public class CommentCreatedEvent {
    private Long id;
    private String contenu;
    private String auteur;
    private Long sujetId;

    public CommentCreatedEvent(Long id, String contenu, String auteur, Long sujetId) {
        this.id = id;
        this.contenu = contenu;
        this.auteur = auteur;
        this.sujetId = sujetId;
    }

    public Long getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public Long getSujetId() {
        return sujetId;
    }

    public LocalDateTime getDateCreation() {
       return LocalDateTime.now();
    }

    // Getters and setters
}
