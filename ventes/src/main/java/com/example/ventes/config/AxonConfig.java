package com.example.ventes.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
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
        xStream.allowTypesByWildcard(new String[] {
                "com.example.coreapi.ventes.**",
        });
        xStream.allowTypes(new Class[] {
                // com.example.ventes.payment.command.Payment.class,
                // com.example.ventes.facture.command.Facture.class,
                com.example.coreapi.ventes.payment.PaymentInfo.class,
                com.example.coreapi.ventes.facture.FactureInfo.class,
        });
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}