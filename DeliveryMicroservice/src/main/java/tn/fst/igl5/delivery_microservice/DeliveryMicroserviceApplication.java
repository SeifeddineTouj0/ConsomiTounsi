package tn.fst.igl5.delivery_microservice;

import com.example.coreapi.delivery.ClaimDTO;
import com.example.coreapi.delivery.OrderDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.Token;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tn.fst.igl5.delivery_microservice.domain.Claim;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {
        "org.axonframework.eventhandling.tokenstore.jpa", // Axon's JPA entities
        "tn.fst.igl5.delivery_microservice",// Your application's JPA entities
},basePackageClasses = {OrderDetailsDTO.class, Claim.class, ClaimDTO.class, Token.class, SagaEntry.class})
public class DeliveryMicroserviceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(DeliveryMicroserviceApplication.class, args);
    }
}
