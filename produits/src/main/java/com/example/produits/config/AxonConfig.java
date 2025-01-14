package com.example.produits.config;

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
        xStream.allowTypesByWildcard(new String[]{
                "com.example.coreapi.produits.**",
        });
        xStream.allowTypes(new Class[] {
                com.example.produits.entities.Produit.class, });
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}
