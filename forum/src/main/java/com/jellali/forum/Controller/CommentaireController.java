package com.jellali.forum.Controller;

import com.jellali.forum.Command.Create.CreateCommentCommand;
import com.jellali.forum.Command.Delete.DeleteCommentCommand;
import com.jellali.forum.Command.Update.UpdateCommentCommand;
import com.jellali.forum.projection.CommentaireProjection;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jellali.forum.Query.GetAllCommentsQuery;
import com.jellali.forum.Query.GetMostRelevantCommentsQuery;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private CommentaireProjection commentaireProjection;


    @PostMapping
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "productmicroService", fallbackMethod = "fallback")
    public CompletableFuture<ResponseEntity<String>> createComment(@RequestBody CreateCommentCommand command) {
        return commandGateway.send(command)
                .thenApply(id -> ResponseEntity.ok("Comment created with ID: " + id))
                .exceptionally(e -> ResponseEntity.badRequest().body(e.getMessage()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteComment(@PathVariable Long id) {
        return commandGateway.send(new DeleteCommentCommand(id))
                .thenApply(result -> ResponseEntity.ok("Comment deleted."))
                .exceptionally(e -> ResponseEntity.internalServerError().body(e.getMessage()));

    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> updateComment(@PathVariable Long id, @RequestParam String newContent) {
        return commandGateway.send(new UpdateCommentCommand(id, newContent))
                .thenApply(result -> ResponseEntity.ok("Comment updated."))
                .exceptionally(e -> ResponseEntity.internalServerError().body(e.getMessage()));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List>> getAllComments() {
        return queryGateway.query(new GetAllCommentsQuery(), List.class)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.internalServerError().build());
    }

    @GetMapping("/relevant")
    public CompletableFuture<ResponseEntity<List>> getMostRelevantComments() {
        return queryGateway.query(new GetMostRelevantCommentsQuery(), List.class)
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.internalServerError().build());
    }
}
