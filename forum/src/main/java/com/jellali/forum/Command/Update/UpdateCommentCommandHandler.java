package com.jellali.forum.Command.Update;

import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Repository.CommentaireRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCommentCommandHandler {
    private final CommentaireRepository commentaireRepository;

    @Autowired
    public UpdateCommentCommandHandler(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    @CommandHandler
    public Commentaire handle(UpdateCommentCommand command) {
        Commentaire commentaire = commentaireRepository.findById(command.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Commentaire non trouvé"));

        commentaire.setContenu(command.getNewContent());
        return commentaireRepository.save(commentaire);
    }
}
