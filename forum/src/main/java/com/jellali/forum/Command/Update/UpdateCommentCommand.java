package com.jellali.forum.Command.Update;

public class UpdateCommentCommand {
    private Long commentId;
    private String newContent;

    // Constructor
    public UpdateCommentCommand(Long commentId, String newContent) {
        this.commentId = commentId;
        this.newContent = newContent;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}
