package com.example.ventes.payment.query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coreapi.ventes.payment.PaymentInfo;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, String> {

}
