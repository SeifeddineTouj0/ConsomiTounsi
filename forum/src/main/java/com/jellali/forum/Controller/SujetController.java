//package com.jellali.forum.Controller;
//
//
//import com.jellali.forum.Command.Create.CreateSujetCommand;
//import com.jellali.forum.Command.Delete.DeleteInactiveSujetsCommand;
//import com.jellali.forum.Command.Delete.DeleteInactiveSujetsCommandHandler;
//import com.jellali.forum.Command.Update.UpdateSujetCommand;
//import com.jellali.forum.Command.Delete.DeleteSujetCommand;
//import com.jellali.forum.Query.*;
//import com.jellali.forum.Entities.Sujet;
//import com.jellali.forum.Command.Create.CreateSujetCommandHandler;
//import com.jellali.forum.Command.Update.UpdateSujetCommandHandler;
//import com.jellali.forum.Command.Delete.DeleteSujetCommandHandler;
//import com.jellali.forum.Repository.SujetRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RestController
//@RequestMapping("/sujets")
//public class SujetController {
//
//    @Autowired
//    private  TestRedundantSujetQueryHandler testRedundantSujetQueryHandler;
//
//    @Autowired
//    private DeleteInactiveSujetsCommandHandler deleteHandler;
//
//    @Autowired
//    private CreateSujetCommandHandler createSujetCommandHandler;
//
//    @Autowired
//    private UpdateSujetCommandHandler updateSujetCommandHandler;
//
//    @Autowired
//    private DeleteSujetCommandHandler deleteSujetCommandHandler;
//
//    @Autowired
//    private GetSujetsQueryHandler getSujetsQueryHandler;
//
//    @Autowired
//    private GetSujetQueryHandler getSujetQueryHandler;
//
//    @Autowired
//    private GetRecentSujetsQueryHandler getRecentSujetsQueryHandler;
//
//    @Autowired
//    private SujetRepository sujetRepository;
//
//
//    @PostMapping
//    public Sujet createSujet(@RequestBody CreateSujetCommand command) {
//        return createSujetCommandHandler.handle(command);
//    }
//
//    @PutMapping("/{id}")
//    public Sujet updateSujet(@PathVariable Long id, @RequestBody UpdateSujetCommand command) {
//        command.setId(id);
//        return updateSujetCommandHandler.handle(command);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteSujet(@PathVariable Long id) {
//        deleteSujetCommandHandler.handle(new DeleteSujetCommand(id));
//    }
//
//    @GetMapping
//    public List<Sujet> getAllSujets() {
//        return getSujetsQueryHandler.handle(new GetSujetsQuery());
//    }
//
//    @GetMapping("/{id}")
//    public Sujet getSujet(@PathVariable Long id) {
//        return getSujetQueryHandler.handle(new GetSujetQuery(id));
//    }
//
//    @GetMapping("/testRedundant")
//    public ResponseEntity<String> testRedundantSujet(@RequestParam String titre) {
//        TestRedundantSujetQuery query = new TestRedundantSujetQuery(titre);
//
//        // Use the query handler to process the query
//        boolean isRedundant = testRedundantSujetQueryHandler.handle(query);
//
//        if (isRedundant) {
//            return ResponseEntity.badRequest().body("Un sujet avec ce titre existe déjà.");
//        } else {
//            return ResponseEntity.ok("Le titre est disponible.");
//        }
//    }
//
//
//    // Endpoint to manually trigger the deletion of inactive subjects
//    @DeleteMapping("/cleanup")
//    public String cleanupInactiveSujets(@RequestParam(defaultValue = "30") int daysAgo) {
//        // You can adjust the "daysAgo" parameter or get it from the request
//        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysAgo);
//        DeleteInactiveSujetsCommand command = new DeleteInactiveSujetsCommand(cutoffDate);
//        deleteHandler.handle(command);
//        return "Cleanup completed for subjects inactive since " + cutoffDate;
//    }
//
//    // Endpoint to fetch the top 3 most recent sujets
//    @GetMapping("/recent")
//    public List<Sujet> getRecentSujets(@RequestParam(defaultValue = "3") int limit) {
//        GetRecentSujetsQuery query = new GetRecentSujetsQuery(limit); // Use the limit parameter
//        return getRecentSujetsQueryHandler.handle(query);
//    }
//}
//


package com.jellali.forum.Controller;

import com.jellali.forum.Command.Create.CreateSujetCommand;
import com.jellali.forum.Command.Delete.DeleteInactiveSujetsCommand;
import com.jellali.forum.Command.Delete.DeleteSujetCommand;
import com.jellali.forum.Entities.Sujet;
import com.jellali.forum.Query.GetSujetQuery;
import com.jellali.forum.Query.GetSujetsQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jellali.forum.Query.GetRecentSujetsQuery;
import com.jellali.forum.Query.TestRedundantSujetQuery;


import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/sujets")
public class SujetController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private  QueryGateway queryGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createSujet(@RequestBody CreateSujetCommand command) {
        commandGateway.sendAndWait(command);
        return "Sujet created successfully";
    }



    @DeleteMapping("/{id}")
    public String deleteSujet(@PathVariable Long id) {

         commandGateway.send(new DeleteSujetCommand(id));
        return "Sujet deleted successfully";

    }

    @DeleteMapping("/inactive")
    public String deleteInactiveSujets() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);
         commandGateway.send(new DeleteInactiveSujetsCommand(cutoffDate));
        return " inactive Sujet deleted ";
    }

    @GetMapping
    public CompletableFuture<List<Sujet>> getAllSujets() {
        return queryGateway.query(new GetSujetsQuery(), ResponseTypes.multipleInstancesOf(Sujet.class));
    }


    @GetMapping("/recent")
    public CompletableFuture<List<Sujet>> getRecentSujets(@RequestParam int limit) {
        return queryGateway.query(new GetRecentSujetsQuery(limit), ResponseTypes.multipleInstancesOf(Sujet.class));
    }

    @GetMapping("/{id}")

    public CompletableFuture<Sujet> getSujet(@PathVariable Long id) {
        return queryGateway.query(new GetSujetQuery(id), ResponseTypes.instanceOf(Sujet.class));


    }

    @GetMapping("/redundant")
    public CompletableFuture<Boolean> checkRedundantSujet(@RequestParam String titre) {
        return queryGateway.query(new TestRedundantSujetQuery(titre), ResponseTypes.instanceOf(Boolean.class));
    }
}

