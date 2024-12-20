package com.kimlngo.ws.clients.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
