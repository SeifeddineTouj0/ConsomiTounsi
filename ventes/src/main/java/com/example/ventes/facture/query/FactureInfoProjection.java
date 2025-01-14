package com.example.ventes.facture.query;

import java.util.concurrent.ExecutionException;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.coreapi.ventes.facture.FactureCreatedEvent;
import com.example.coreapi.ventes.facture.FactureDeletedEvent;
import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.facture.FactureInfoNamedQueries;
import com.example.coreapi.ventes.facture.FactureUpdatedEvent;
import com.example.coreapi.ventes.facture.TypeFacture;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.PaymentInfoNamedQueries;
import com.example.ventes.facture.utils.FacturePdfGenerator;

@Component
public class FactureInfoProjection {
    private final FactureInfoRepository factureInfoRepository;
    private final QueryGateway queryGateway;
    private final FacturePdfGenerator facturePdfGenerator;

    public FactureInfoProjection(FactureInfoRepository factureInfoRepository, QueryGateway queryGateway,
            FacturePdfGenerator facturePdfGenerator) {
        this.factureInfoRepository = factureInfoRepository;
        this.queryGateway = queryGateway;
        this.facturePdfGenerator = facturePdfGenerator;
    }

    @EventHandler
    public void on(FactureCreatedEvent event) {
        PaymentInfo paymentInfo;
        try {
            paymentInfo = queryGateway.query(
                    PaymentInfoNamedQueries.FIND_ONE,
                    event.getPaymentId(),
                    PaymentInfo.class).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch PaymentInfo", e);
        }

        FactureInfo factureInfo = new FactureInfo();
        factureInfo.setFactureId(event.getFactureId());
        factureInfo.setDateFacture(event.getDateFacture());
        factureInfo.setTypeFacture(event.getTypeFacture());
        factureInfo.setPaymentId(event.getPaymentId());
        factureInfo.setUser(paymentInfo.getUser());
        factureInfo.setMontant(paymentInfo.getMontant());
        factureInfo.setProducts(paymentInfo.getProducts());

        factureInfoRepository.save(factureInfo);
    }

    @EventHandler
    public void on(FactureUpdatedEvent event) {
        FactureInfo factureInfo = factureInfoRepository.findById(event.getFactureId()).orElse(null);
        if (factureInfo == null) {
            throw new RuntimeException("Facture not found");
        }

        PaymentInfo paymentInfo;
        try {
            paymentInfo = queryGateway.query(
                    PaymentInfoNamedQueries.FIND_ONE,
                    event.getPaymentId(),
                    PaymentInfo.class).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch PaymentInfo", e);
        }

        factureInfo.setDateFacture(event.getDateFacture());
        factureInfo.setTypeFacture(event.getTypeFacture());
        factureInfo.setPaymentId(event.getPaymentId());
        factureInfo.setUser(paymentInfo.getUser());
        factureInfo.setMontant(paymentInfo.getMontant());
        factureInfo.setProducts(paymentInfo.getProducts());

        factureInfoRepository.save(factureInfo);
    }

    @EventHandler
    public void on(FactureDeletedEvent event) {
        factureInfoRepository.deleteById(event.getFactureId());
    }

    @QueryHandler(queryName = FactureInfoNamedQueries.FIND_ALL)
    public Iterable<FactureInfo> handle() {
        return factureInfoRepository.findAll();
    }

    @QueryHandler(queryName = FactureInfoNamedQueries.FIND_ONE)
    public FactureInfo handle(String factureId) {
        return factureInfoRepository.findById(factureId).orElse(null);
    }

    @QueryHandler(queryName = FactureInfoNamedQueries.FIND_FACTURE_BY_USER)
    public Iterable<FactureInfo> handleFactureByUser(String userId) {
        return factureInfoRepository.findByUser(userId);
    }

    @QueryHandler(queryName = FactureInfoNamedQueries.FIND_FACTURE_BY_TYPE)
    public Iterable<FactureInfo> handleFactureByType(TypeFacture typeFacture) {
        return factureInfoRepository.findByTypeFacture(typeFacture);
    }

    @QueryHandler(queryName = FactureInfoNamedQueries.GENERATE_FACTURE_PDF)
    public byte[] handleGenerateFacturePdf(String factureId) {
        FactureInfo factureInfo = factureInfoRepository.findById(factureId).orElse(null);
        if (factureInfo == null) {
            throw new RuntimeException("Facture not found");
        }

        return facturePdfGenerator.generatePdf(factureInfo);

    }

}
