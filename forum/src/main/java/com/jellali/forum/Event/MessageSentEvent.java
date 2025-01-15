package com.jellali.forum.Event;


import java.time.LocalDateTime;

public class MessageSentEvent {
    private Long messageId;
    private String contenu;
    private String sender;
    private String receiver;
    private LocalDateTime timestamp;

    public MessageSentEvent(Long messageId, String contenu, String sender, String receiver) {
        this.messageId = messageId;
        this.contenu = contenu;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Object getContenu() {
        return contenu;
    }
    public String getReceiver() {
        return receiver;
    }

    public Object getSender() {
        return sender;
    }


    // Getters and Setters
}
