package com.example.ventes.payment.command;

import java.time.LocalDateTime;
import java.util.Set;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.coreapi.ventes.payment.*;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Payment {

    @AggregateIdentifier
    private String paymentId;

    private TypePayment typePayment;

    private Double montant;

    private LocalDateTime datePayment;

    private StatusPaiment statusPayment;

    private String user;

    private Set<PurchasedProduct> produits;

    public Payment() {
    }

    @CommandHandler
    public Payment(CreatePaymentCommand command) {
        // TODO: Add logic to validate the command
        apply(new PaymentCreatedEvent(command.paymentId(), command.typePayment(), command.montant(),
                command.datePayment(), command.statusPayment(), command.userId(), command.produitIds()));
    }

    @EventSourcingHandler
    protected void on(PaymentCreatedEvent event) {
        this.paymentId = event.paymentId();
        this.typePayment = event.typePayment();
        this.montant = event.montant();
        this.datePayment = event.datePayment();
        this.statusPayment = event.statusPayment();
        this.user = event.userId();
        this.produits = event.produitIds();
    }

}
