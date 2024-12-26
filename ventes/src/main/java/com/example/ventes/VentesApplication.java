package com.example.ventes;

import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.example.coreapi.ventes.payment.PaymentInfo;

@EntityScan(basePackageClasses = { PaymentInfo.class, SagaEntry.class, TokenEntry.class })
@SpringBootApplication
public class VentesApplication {

	public static void main(final String[] args) {
		SpringApplication.run(VentesApplication.class, args);
	}

}
