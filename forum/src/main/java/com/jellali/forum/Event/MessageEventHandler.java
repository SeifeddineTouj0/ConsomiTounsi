package com.jellali.forum.Event;


import com.jellali.forum.Entities.ChatMessage;
import com.jellali.forum.Event.MessageSentEvent;
import com.jellali.forum.Repository.ChatMessageRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageEventHandler {

    private final ChatMessageRepository chatMessageRepository;

    public MessageEventHandler(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @EventHandler
    public void on(MessageSentEvent event) {
        ChatMessage message = new ChatMessage(
                event.getContenu(),
                event.getSender(),
                event.getReceiver(),
                event.getTimestamp()
        );
        chatMessageRepository.save(message);
    }
}

