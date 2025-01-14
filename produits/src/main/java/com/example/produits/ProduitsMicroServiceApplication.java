package com.example.produits;

import com.example.produits.entities.Produit;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = {
        Produit.class,
        SagaEntry.class
        , TokenEntry.class
})
@SpringBootApplication
//@EnableDiscoveryClient
public class ProduitsMicroServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProduitsMicroServiceApplication.class, args);
    }
}
