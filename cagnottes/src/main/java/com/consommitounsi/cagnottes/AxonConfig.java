package com.consommitounsi.cagnottes;


import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Primary
    @Bean
    public Serializer serializer() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{
                "com.example.coreapi.boutique.stock.**",
                "com.consommitounsi.cagnottes.**",
        });
        xStream.allowTypes(new Class[]{
//                com.example.boutiques.entities.Rayon.class,
//                com.example.boutiques.entities.Stock.class
                com.consommitounsi.cagnottes.donations.entities.Donation.class,
                com.consommitounsi.cagnottes.donations.entities.DonationProduct.class,
                com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte.class

        });
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}
