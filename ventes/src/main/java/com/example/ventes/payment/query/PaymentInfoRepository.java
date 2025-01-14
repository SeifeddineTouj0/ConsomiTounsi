package com.example.ventes.payment.query;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.coreapi.ventes.payment.PaymentInfo;
import com.example.coreapi.ventes.payment.TypePayment;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, String> {

    Iterable<PaymentInfo> findByTypePayment(TypePayment typePayment);

    Iterable<PaymentInfo> findByUser(String userId);

    @Query("SELECT p FROM PaymentInfo p JOIN p.products prod WHERE prod IN :productIds")
    Iterable<PaymentInfo> findByProductIds(Set<String> productIds);

}
