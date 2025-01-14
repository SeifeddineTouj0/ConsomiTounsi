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
        xStream.allowTypesByWildcard(new String[] {
                "com.example.coreapi.**",
                "com.example.ventes.**",
        });
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}