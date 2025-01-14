package com.example.ventes.payment.query;

import java.util.Set;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.coreapi.ventes.payment.PaymentCreatedEvent;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.PaymentInfoNamedQueries;
import com.example.coreapi.ventes.payment.TypePayment;

@Component
public class PaymentInfoProjection {
    private final PaymentInfoRepository repository;

    public PaymentInfoProjection(PaymentInfoRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(PaymentCreatedEvent event) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentId(event.getPaymentId());
        paymentInfo.setTypePayment(event.getTypePayment());
        paymentInfo.setMontant(event.getMontant());
        paymentInfo.setDatePayment(event.getDatePayment());
        paymentInfo.setStatusPayment(event.getStatusPayment());
        paymentInfo.setUser(event.getUserId());
        paymentInfo.setProducts(event.getProduitIds());

        repository.save(paymentInfo);
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_ALL)
    public Iterable<PaymentInfo> handle() {
        return repository.findAll();
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_ONE)
    public PaymentInfo handle(String paymentId) {
        return repository.findById(paymentId).orElse(null);
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_ONLINE_PAYMENT)
    public Iterable<PaymentInfo> handleOnlinePayment() {
        return repository.findByTypePayment(TypePayment.EN_LIGNE);
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_DELIVERY_PAYMENT)
    public Iterable<PaymentInfo> handleDeliveryPayment() {
        return repository.findByTypePayment(TypePayment.PORTE_A_PORTE);
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_PAYMENT_BY_USER)
    public Iterable<PaymentInfo> handlePaymentByUser(String userId) {
        return repository.findByUser(userId);
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_PAYMENT_BY_PRODUCT)
    public Iterable<PaymentInfo> handlePaymentByProduct(Set<String> productIds) {
        return repository.findByProductIds(productIds);
    }
}
