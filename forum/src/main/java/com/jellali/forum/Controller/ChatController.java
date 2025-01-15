package com.jellali.forum.Controller;

import com.jellali.forum.Entities.ChatMessage;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.jellali.forum.Command.Create.SendMessageCommand;

import java.util.UUID;

@Controller
public class ChatController {

    private final CommandGateway commandGateway;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(CommandGateway commandGateway, SimpMessagingTemplate messagingTemplate) {
        this.commandGateway = commandGateway;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "productmicroService", fallbackMethod = "fallback")
    public void sendMessage(ChatMessage message) {
        String messageId = UUID.randomUUID().toString();

        // Send command to Aggregate
        commandGateway.send(new SendMessageCommand(
                messageId, message.getSenderId(), message.getReceiverId(), message.getContent()));

        // Push message to receiver's WebSocket channel
        messagingTemplate.convertAndSendToUser(message.getReceiverId(), "/queue/messages", message);
    }
}
