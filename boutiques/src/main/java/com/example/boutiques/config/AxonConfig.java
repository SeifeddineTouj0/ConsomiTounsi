package com.example.boutiques.config;

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
                                "com.example.coreapi.**",
                                "com.example.boutiques.**",
                });
                xStream.allowTypes(new Class[] {
                                com.example.boutiques.entities.Rayon.class,
                                com.example.boutiques.entities.Stock.class });
                return XStreamSerializer.builder()
                                .xStream(xStream)
                                .build();
        }
}
