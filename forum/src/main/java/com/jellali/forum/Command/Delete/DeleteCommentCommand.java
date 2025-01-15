package com.jellali.forum.Command.Delete;

public class DeleteCommentCommand {
    private Long commentId;

    public Long getCommentId() {
        return commentId;
    }

    public DeleteCommentCommand(Long commentId) {
        this.commentId = commentId;
    }
}
