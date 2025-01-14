package com.example.ventes.payment.command;

import java.time.LocalDateTime;
import java.util.Set;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.coreapi.boutique.stock.queries.GetStockStatusQuery;
import com.example.coreapi.boutique.stock.responses.StockStatusResponse;
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

    @Autowired
    private final QueryGateway queryGateway;

    public Payment() {
        this.queryGateway = null; // Default constructor for Axon
    }

    @CommandHandler
    public Payment(CreatePaymentCommand command, QueryGateway queryGateway) {
        this.queryGateway = queryGateway;

        for (PurchasedProduct produit : command.getProduitIds()) {
            if (produit.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantite doit etre positive");
            }
            StockStatusResponse stockStatusResponse = queryGateway.query(
                    new GetStockStatusQuery(produit.getProductId()), StockStatusResponse.class).join();
            if (stockStatusResponse == null) {
                throw new IllegalArgumentException("Produit non trouve dans le stock");
            }
            if (stockStatusResponse.getQuantity() < produit.getQuantity()) {
                throw new IllegalArgumentException("Quantite insuffisante");
            }
        }

        apply(new PaymentCreatedEvent(command.getPaymentId(), command.getTypePayment(), command.getMontant(),
                command.getDatePayment(), command.getStatusPayment(), command.getUserId(), command.getProduitIds()));
    }

    @EventSourcingHandler
    protected void on(PaymentCreatedEvent event) {
        this.paymentId = event.getPaymentId();
        this.typePayment = event.getTypePayment();
        this.montant = event.getMontant();
        this.datePayment = event.getDatePayment();
        this.statusPayment = event.getStatusPayment();
        this.user = event.getUserId();
        this.produits = event.getProduitIds();
    }

}
