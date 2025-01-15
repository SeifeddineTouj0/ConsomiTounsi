package com.jellali.forum.aggregates;

import com.jellali.forum.Event.MessageSentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.queryhandling.QueryGateway;
import com.jellali.forum.Command.Create.SendMessageCommand;
import java.util.concurrent.ExecutionException;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ChatMessageAggregate {

    @AggregateIdentifier
    private String messageId;

    private String senderId;
    private String receiverId;
    private String content;

    public ChatMessageAggregate() {}

    @CommandHandler
    public ChatMessageAggregate(SendMessageCommand command, QueryGateway queryGateway) throws ExecutionException, InterruptedException {
        // Validate receiver via QueryGateway
        Boolean userExists = queryGateway.query(
                "findUserById", command.getReceiverId(), Boolean.class).get();

        if (Boolean.FALSE.equals(userExists)) {
            throw new IllegalArgumentException("Receiver user does not exist!");
        }

        apply(new MessageSentEvent(command.getMessageId(), command.getSenderId(), command.getReceiverId(), command.getContent()));
    }

    @EventSourcingHandler
    public void on(MessageSentEvent event) {
        this.messageId = String.valueOf(event.getMessageId());
        this.senderId = event.getSenderId();
        this.receiverId = event.getReceiverId();
        this.content = event.getContent();
    }
}

