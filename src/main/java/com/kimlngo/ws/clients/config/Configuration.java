package com.kimlngo.ws.clients.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     *
     * @param clientRegistrationRepository
     * @param clientRepository
     * @return
     * Note: this WebClient is not meant to be used to communicate with external third-party service
     * because it might send the access token to external party and compromised.
     */
    @Bean
    public WebClient createWebClient(ClientRegistrationRepository clientRegistrationRepository,
                                     OAuth2AuthorizedClientRepository clientRepository) {
        var filterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrationRepository,
                clientRepository
        );

        filterFunction.setDefaultOAuth2AuthorizedClient(true);

        return WebClient.builder().apply(filterFunction.oauth2Configuration()).build();
    }
}
