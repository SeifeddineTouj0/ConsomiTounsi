package com.jellali.forum.Command.Delete;

import com.jellali.forum.Repository.CommentaireRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCommentCommandHandler {
    private final CommentaireRepository commentaireRepository;

    @Autowired
    public DeleteCommentCommandHandler(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    @CommandHandler
    public void handle(DeleteCommentCommand command) {
        commentaireRepository.deleteById(command.getCommentId());
    }
}
