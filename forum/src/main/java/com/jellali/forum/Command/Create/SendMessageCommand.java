package com.jellali.forum.Command.Create;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class SendMessageCommand {
    @TargetAggregateIdentifier
    private Long messageId;
    private String contenu;
    private String sender;
    private String receiver;

    public SendMessageCommand(String messageId, String contenu, String sender, String receiver) {
        this.messageId = Long.valueOf(messageId);
        this.contenu = contenu;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getContent() {
        return contenu;
    }

    public String getSenderId() {
        return sender;
    }

    public String getReceiverId() {
        return receiver;
    }


    // Constructor, getters and setters
}

