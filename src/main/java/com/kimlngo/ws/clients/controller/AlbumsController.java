package com.kimlngo.ws.clients.controller;

import com.kimlngo.ws.clients.response.AlbumRest;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Arrays;

@Controller
public class AlbumsController {

    @Autowired
    private OAuth2AuthorizedClientService oauth2ClientService;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient authorizedClient = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
        System.out.println("JWT Access Token: " + tokenValue);

        System.out.println("Principal = " + principal);

        var idToken = principal.getIdToken();
        var idTokenValue = idToken.getTokenValue();
        System.out.println("idTokenValue = " + idTokenValue);

        AlbumRest albumOne = new AlbumRest();
        albumOne.setAlbumId("albumOne");
        albumOne.setAlbumTitle("Album One Title");
        albumOne.setAlbumUrl("http://localhost:8082/albums/1");

        AlbumRest albumTwo = new AlbumRest();
        albumTwo.setAlbumId("albumTwo");
        albumTwo.setAlbumTitle("Album Two Title");
        albumTwo.setAlbumUrl("http://localhost:8082/albums/2");

        model.addAttribute("albums", Arrays.asList(albumOne, albumTwo));

        return "albums";
    }
}
