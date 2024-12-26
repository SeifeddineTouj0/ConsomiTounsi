package com.example.ventes.payment.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.coreapi.ventes.payment.PaymentCreatedEvent;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.PaymentInfoNamedQueries;

@Component
public class PaymentInfoProjection {
    private final PaymentInfoRepository repository;

    public PaymentInfoProjection(PaymentInfoRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(PaymentCreatedEvent event) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentId(event.paymentId());
        paymentInfo.setTypePayment(event.typePayment());
        paymentInfo.setMontant(event.montant());
        paymentInfo.setDatePayment(event.datePayment());
        paymentInfo.setStatusPayment(event.statusPayment());
        paymentInfo.setUser(event.userId());
        paymentInfo.setProduits(event.produitIds());

        repository.save(paymentInfo);
    }

    @QueryHandler(queryName = PaymentInfoNamedQueries.FIND_ALL)
    public Iterable<PaymentInfo> handle() {
        return repository.findAll();
    }
}
