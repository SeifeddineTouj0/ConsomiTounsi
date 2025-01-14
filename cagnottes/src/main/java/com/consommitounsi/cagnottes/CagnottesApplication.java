package com.consommitounsi.cagnottes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class CagnottesApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CagnottesApplication.class, args);
    }

}
