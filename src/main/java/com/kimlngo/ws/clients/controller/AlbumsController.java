package com.kimlngo.ws.clients.controller;

import com.kimlngo.ws.clients.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    private OAuth2AuthorizedClientService oauth2ClientService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient authorizedClient = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        String jwtAccessToken = authorizedClient.getAccessToken().getTokenValue();
        System.out.println("JWT Access Token: " + jwtAccessToken);

        System.out.println("Principal = " + principal);

        var idToken = principal.getIdToken();
        var idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);

        String url = "http://localhost:8082/albums";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<List<AlbumRest>> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<List<AlbumRest>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<List<AlbumRest>>() {
                });

        List<AlbumRest> albumRests = responseEntity.getBody();

        model.addAttribute("albums", albumRests);

        return "albums";
    }
}
