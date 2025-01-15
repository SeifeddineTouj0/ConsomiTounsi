package com.jellali.forum.Entities;


import com.jellali.forum.Event.CommentCreatedEvent;
import jakarta.persistence.*;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import java.time.LocalDateTime;
import java.util.List;
//@Entity
//@Aggregate
//@Table(name = "commentaires")
//public class Commentaire {
//    @Id
//    @AggregateIdentifier
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, length = 2000)
//    private String contenu;
//    @Column(nullable = false)
//    private String auteur;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime dateCreation;
//
//    @ManyToOne
//    @JoinColumn(name = "sujet_id", nullable = false)
//    private Sujet sujet;
//
//    @OneToMany(mappedBy = "commentaire", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Evaluation> evaluations;
//
//    public void setId(Long id) {
//        this.id = id;
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
//    public void setSujet(Sujet sujet) {
//        this.sujet = sujet;
//    }
//
//    public void setEvaluations(List<Evaluation> evaluations) {
//        this.evaluations = evaluations;
//    }
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
//    public Sujet getSujet() {
//        return sujet;
//    }
//
//    public List<Evaluation> getEvaluations() {
//        return evaluations;
//    }
//
//// Getters and Setters
//
//    public Commentaire() {}
//
//    public Commentaire(Long id, String contenu, String auteur, Sujet sujet, LocalDateTime dateCreation) {
//        this.id = id;
//        this.contenu = contenu;
//        this.auteur = auteur;
//        this.sujet = sujet;
//        this.dateCreation = dateCreation;
//    }
//
//    // Factory methods for command handling
//
//    public static Commentaire create(String contenu, String auteur, Sujet sujet, LocalDateTime dateCreation) {
//        Commentaire commentaire = new Commentaire();
//        commentaire.contenu = contenu;
//        commentaire.auteur = auteur;
//        commentaire.sujet = sujet;
//        commentaire.dateCreation = dateCreation;
//        return commentaire;
//    }
//
//    @EventSourcingHandler
//    public void on(CommentCreatedEvent event) {
//        this.id = event.getId();
//        this.contenu = event.getContenu();
//        this.auteur = event.getAuteur();
//        this.dateCreation = LocalDateTime.now();
//    }
//}
//
//



import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String contenu;

    @Column(nullable = false)
    private String auteur;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "sujet_id", nullable = false)
    private Sujet sujet;

    @OneToMany(mappedBy = "commentaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations;

    private Commentaire(String contenu, String auteur, Sujet sujet, LocalDateTime dateCreation) {
        this.contenu = contenu;
        this.auteur = auteur;
        this.sujet = sujet;
        this.dateCreation = dateCreation;
    }

    public Commentaire() {

    }

    // Static factory method
    public static Commentaire create(String contenu, String auteur, Sujet sujet, LocalDateTime dateCreation) {
        return new Commentaire(contenu, auteur, sujet, dateCreation);
    }

    // Getters and setters
    public Long getId() {
        return id;
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

    public Sujet getSujet() {
        return sujet;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}