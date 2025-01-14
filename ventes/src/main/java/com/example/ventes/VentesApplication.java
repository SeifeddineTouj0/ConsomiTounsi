package com.example.ventes;

import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.payment.PaymentInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@EntityScan(basePackageClasses = { PaymentInfo.class, FactureInfo.class, SagaEntry.class, TokenEntry.class })
@SpringBootApplication
public class VentesApplication {

	public static void main(final String[] args) {
		SpringApplication.run(VentesApplication.class, args);
	}

	@Autowired
	public void configureSerializers(ObjectMapper objectMapper) {
		objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
				ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
	}

}
