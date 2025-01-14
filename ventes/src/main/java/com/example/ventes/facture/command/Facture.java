package com.example.ventes.facture.command;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.queryhandling.QueryGateway;

import com.example.coreapi.ventes.facture.CreateFactureCommand;
import com.example.coreapi.ventes.facture.DeleteFactureCommand;
import com.example.coreapi.ventes.facture.FactureCreatedEvent;
import com.example.coreapi.ventes.facture.FactureDeletedEvent;
import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.facture.FactureInfoNamedQueries;
import com.example.coreapi.ventes.facture.FactureUpdatedEvent;
import com.example.coreapi.ventes.facture.TypeFacture;
import com.example.coreapi.ventes.facture.UpdateFactureCommand;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.PaymentInfoNamedQueries;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate
public class Facture {

    @AggregateIdentifier
    private String id;

    private LocalDate dateFacture;

    private TypeFacture typeFacture;

    private String paymentId;

    @Autowired
    private final QueryGateway queryGateway;

    public Facture() {
        this.queryGateway = null; // Default constructor for Axon
    }

    @CommandHandler
    public Facture(CreateFactureCommand command, QueryGateway queryGateway)
            throws ExecutionException, InterruptedException {
        this.queryGateway = queryGateway;

        // Fetch PaymentInfo using QueryGateway
        PaymentInfo paymentInfo = queryGateway.query(
                PaymentInfoNamedQueries.FIND_ONE,
                command.paymentId(),
                PaymentInfo.class).get();

        if (paymentInfo == null) {
            throw new IllegalArgumentException("Payment not found");
        }

        // Apply FactureCreatedEvent with additional fields
        apply(new FactureCreatedEvent(
                command.factureId(),
                command.dateFacture(),
                command.typeFacture(),
                command.paymentId()));
    }

    @CommandHandler
    public void handle(UpdateFactureCommand command)
            throws ExecutionException, InterruptedException {

        // Check if facture exists
        FactureInfo factureInfo = queryGateway.query(
                FactureInfoNamedQueries.FIND_ONE,
                command.factureId(),
                FactureInfo.class).get();

        if (factureInfo == null) {
            throw new IllegalArgumentException("Facture not found");
        }

        // Fetch PaymentInfo using QueryGateway
        PaymentInfo paymentInfo = queryGateway.query(
                PaymentInfoNamedQueries.FIND_ONE,
                command.paymentId(),
                PaymentInfo.class).get();

        if (paymentInfo == null) {
            throw new IllegalArgumentException("Payment not found");
        }

        // Apply FactureUpdatedEvent with additional fields
        apply(new FactureUpdatedEvent(
                command.factureId(),
                command.dateFacture(),
                command.typeFacture(),
                command.paymentId()));
    }

    @CommandHandler
    public void handle(DeleteFactureCommand command) {
        apply(new FactureDeletedEvent(command.factureId()));
    }

    @EventSourcingHandler
    protected void on(FactureCreatedEvent event) {
        this.id = event.factureId();
        this.dateFacture = event.dateFacture();
        this.typeFacture = event.typeFacture();
        this.paymentId = event.paymentId();
    }

    @EventSourcingHandler
    protected void on(FactureUpdatedEvent event) {
        this.id = event.factureId();
        this.dateFacture = event.dateFacture();
        this.typeFacture = event.typeFacture();
        this.paymentId = event.paymentId();
    }

    @EventSourcingHandler
    protected void on(FactureDeletedEvent event) {
        this.id = event.factureId();
        markDeleted();
    }
}
