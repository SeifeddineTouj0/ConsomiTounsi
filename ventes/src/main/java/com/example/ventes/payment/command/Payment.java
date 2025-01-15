package com.example.ventes.payment.command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.coreapi.boutique.stock.commands.UpdateStockCommand;
import com.example.coreapi.boutique.stock.queries.GetStockStatusQuery;
import com.example.coreapi.boutique.stock.responses.StockStatusResponse;
import com.example.coreapi.delivery.GetDeliveryFeesQuery;
import com.example.coreapi.delivery.OrderDetailsDTO;
import com.example.coreapi.produits.queries.FetchproductByIdQuery;
import com.example.coreapi.produits.queries.ProductInfo;
import com.example.coreapi.users.queries.FetchUserByIdQuery;
import com.example.coreapi.users.queries.UserInfo;
import com.example.coreapi.ventes.payment.*;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Payment {

    @AggregateIdentifier
    private String paymentId;

    private TypePayment typePayment;

    private LocalDateTime datePayment;

    private StatusPaiment statusPayment;

    private String user;

    private Double montant;

    private Double deliveryFees;

    private Set<PurchasedProduct> produits;

    private double userAdressLong;

    private double userAdressLat;

    @Autowired
    private final QueryGateway queryGateway;

    @Autowired
    private final CommandGateway commandGateway;

    public Payment() {
        this.queryGateway = null; // Default constructor for Axon
        this.commandGateway = null;
    }

    @CommandHandler
    public Payment(CreatePaymentCommand command, QueryGateway queryGateway, CommandGateway commandGateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;

        // Check if the user exists
        UserInfo userInfo = queryGateway.query(new FetchUserByIdQuery(command.getUserId()), UserInfo.class).join();
        if (userInfo == null) {
            throw new IllegalArgumentException("Utilisateur non trouve");
        }

        // Check if the products are valid and in Stock
        Map<String, StockStatusResponse> productStockIdMap = new HashMap<>();
        List<ProductInfo> productInfos = new ArrayList<>();
        Double total = (double) 0;

        for (PurchasedProduct produit : command.getProduitIds()) {
            if (produit.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantite doit etre positive");
            }

            ProductInfo productInfo = queryGateway.query(
                    new FetchproductByIdQuery(produit.getProductId()), ProductInfo.class).join();
            if (productInfo == null) {
                throw new IllegalArgumentException("Produit non trouve");
            }
            productInfos.add(productInfo);

            total += productInfo.getPrice() * produit.getQuantity();

            StockStatusResponse stockStatusResponse = queryGateway.query(
                    new GetStockStatusQuery(produit.getProductId()), StockStatusResponse.class).join();
            if (stockStatusResponse == null) {
                throw new IllegalArgumentException("Produit non trouve dans le stock");
            }
            if (stockStatusResponse.getQuantity() < produit.getQuantity()) {
                throw new IllegalArgumentException("Quantite insuffisante");
            }
            productStockIdMap.put(produit.getProductId(), stockStatusResponse);
        }

        // Calculate Delivery cost
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(command.getUserAdressLat(), command.getUserAdressLong(),
                productInfos);
        Double deliveryFees = queryGateway.query(new GetDeliveryFeesQuery(orderDetailsDTO), Double.class).join();
        total += deliveryFees;

        // Update stock
        for (PurchasedProduct produit : command.getProduitIds()) {
            commandGateway.send(new UpdateStockCommand(productStockIdMap.get(produit.getProductId()).getStockId(),
                    produit.getProductId(),
                    productStockIdMap.get(produit.getProductId()).getQuantity() - produit.getQuantity()));
        }

        apply(new PaymentCreatedEvent(command.getPaymentId(), command.getTypePayment(), total, deliveryFees,
                command.getDatePayment(), command.getStatusPayment(), command.getUserId(), command.getProduitIds(),
                command.getUserAdressLong(), command.getUserAdressLat()));
    }

    @EventSourcingHandler
    protected void on(PaymentCreatedEvent event) {
        this.paymentId = event.getPaymentId();
        this.typePayment = event.getTypePayment();
        this.montant = event.getMontant();
        this.deliveryFees = event.getDeliveryFees();
        this.datePayment = event.getDatePayment();
        this.statusPayment = event.getStatusPayment();
        this.user = event.getUserId();
        this.produits = event.getProduitIds();
    }

}
