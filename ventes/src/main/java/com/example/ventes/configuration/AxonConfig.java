package com.example.ventes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        // xStream.allowTypesByWildcard(new String[] {
        // "com.example.**"
        // });
        xStream.addPermission(AnyTypePermission.ANY);

        return xStream;
    }

}