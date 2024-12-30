package tn.fst.igl5.delivery_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {
        "org.axonframework.eventhandling.tokenstore.jpa", // Axon's JPA entities
        "tn.fst.igl5.delivery_microservice" // Your application's JPA entities
})
public class DeliveryMicroserviceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(DeliveryMicroserviceApplication.class, args);
    }
}
