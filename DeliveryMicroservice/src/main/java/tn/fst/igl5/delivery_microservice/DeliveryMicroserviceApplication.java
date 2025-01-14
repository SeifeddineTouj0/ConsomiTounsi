package tn.fst.igl5.delivery_microservice;

import com.example.coreapi.delivery.OrderDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {
        "org.axonframework.eventhandling.tokenstore.jpa", // Axon's JPA entities
        "tn.fst.igl5.delivery_microservice",// Your application's JPA entities
},basePackageClasses = {OrderDetailsDTO.class})
public class DeliveryMicroserviceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(DeliveryMicroserviceApplication.class, args);
    }

    @Autowired
    public void configureSerializers(ObjectMapper objectMapper) {
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    }
}
