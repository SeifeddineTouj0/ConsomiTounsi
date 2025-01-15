package com.jellali.forum.Query;

import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Repository.CommentaireRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireQueryHandler {

    private final CommentaireRepository commentaireRepository;

    @Autowired
    public CommentaireQueryHandler(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    // Handle GetAllCommentsQuery
    @QueryHandler
    public List<Commentaire> handle(GetAllCommentsQuery query) {
        return commentaireRepository.findAll();
    }

    // Handle GetMostRelevantCommentsQuery
    @QueryHandler
    public List<Commentaire> handle(GetMostRelevantCommentsQuery query) {
        return commentaireRepository.findTop10ByOrderByDateCreationDesc(); // Example logic for most relevant comments
    }
}
