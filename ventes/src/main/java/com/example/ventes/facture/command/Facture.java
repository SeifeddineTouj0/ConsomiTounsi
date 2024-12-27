package com.example.ventes.facture.command;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.queryhandling.QueryGateway;

import com.example.coreapi.ventes.facture.CreateFactureCommand;
import com.example.coreapi.ventes.facture.FactureCreatedEvent;
import com.example.coreapi.ventes.facture.TypeFacture;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.PaymentInfoNamedQueries;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Facture {

    @AggregateIdentifier
    private String id;

    private LocalDate dateFacture;

    private TypeFacture typeFacture;

    private String paymentId;

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

    @EventSourcingHandler
    protected void on(FactureCreatedEvent event) {
        this.id = event.factureId();
        this.dateFacture = event.dateFacture();
        this.typeFacture = event.typeFacture();
        this.paymentId = event.paymentId();
    }
}
