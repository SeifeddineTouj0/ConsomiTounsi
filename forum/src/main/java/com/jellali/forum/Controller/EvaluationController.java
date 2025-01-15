package com.jellali.forum.Controller;

import com.jellali.forum.Command.Create.AddEvaluationCommand;
import com.jellali.forum.Entities.Evaluation;
import com.jellali.forum.Query.GetEvaluationsByRatingQuery;
import com.jellali.forum.Query.GetEvaluationsByReactionQuery;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/add")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "productmicroService", fallbackMethod = "fallback")
    public ResponseEntity<String> addEvaluation(@RequestBody AddEvaluationCommand command) {
        try {
            // Send the command via the command gateway
            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Evaluation added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while adding evaluation.");
        }
    }

    // Endpoint for fetching evaluations by rating range
    @GetMapping("/by-rating")
    public ResponseEntity<List<Evaluation>> getEvaluationsByRating(
            @RequestParam int minRating,
            @RequestParam int maxRating) {
        GetEvaluationsByRatingQuery query = new GetEvaluationsByRatingQuery(minRating, maxRating);
        List<Evaluation> evaluations = queryGateway.query(query, List.class).join();
        return ResponseEntity.ok(evaluations);
    }

    // Endpoint for fetching evaluations by reaction
    @GetMapping("/by-reaction")
    public ResponseEntity<List<Evaluation>> getEvaluationsByReaction(@RequestParam String reaction) {
        Evaluation.React react = Evaluation.React.valueOf(reaction.toUpperCase());
        GetEvaluationsByReactionQuery query = new GetEvaluationsByReactionQuery(react);
        List<Evaluation> evaluations = queryGateway.query(query, List.class).join();
        return ResponseEntity.ok(evaluations);
    }
}
